package com.logituit.moviedbadi.data.local.upcoming

import androidx.paging.PagingSource
import androidx.room.*
import com.logituit.moviedbadi.data.local.common.CommonMovieListDao
import com.logituit.moviedbadi.data.local.movie.MovieEntity
import com.logituit.moviedbadi.data.remote.STARTING_PAGE_INDEX
import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse
import com.logituit.moviedbadi.data.repository.upcoming.UpcomingMoviesLocalDataSource
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@Dao
interface UpcomingMoviesDao : CommonMovieListDao, UpcomingMoviesLocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<UpcomingMovieKeysEntity>)

    @Query("SELECT * FROM upcoming_movies_keys WHERE movieId = :id")
    override suspend fun getRemoteKeysForMovieId(id: Long): UpcomingMovieKeysEntity?

    @Query("DELETE FROM upcoming_movies_keys")
    suspend fun clearRemoteKeys()

    @Query(
        """SELECT movies.* FROM movies
        INNER JOIN upcoming_movies_keys ON movies.id=upcoming_movies_keys.movieId
        ORDER BY curKey ASC, popularity DESC, title ASC"""
    )
    override fun getAll(): PagingSource<Int, MovieEntity>

    @Transaction
    override suspend fun cacheResponse(
        response: MoviePagedListResponse,
        pageKey: Int,
        isRefresh: Boolean,
    ) {
        println("imnimn here 11")
        if (isRefresh) {
            clearRemoteKeys()
        }
        val prevKey = if (pageKey == STARTING_PAGE_INDEX) null else pageKey - 1
        val nextKey = if (pageKey >= response.totalPages) null else pageKey + 1
        val keys = response.results.map { UpcomingMovieKeysEntity(it.id, prevKey, pageKey, nextKey) }
        insertAll(keys)
        insertAllMovies(response.toMovieEntityList())
        println("imnimn here 12")
    }
}