package com.logituit.moviedbadi.data.repository.upcoming

import androidx.paging.PagingSource
import com.logituit.moviedbadi.data.local.movie.MovieEntity
import com.logituit.moviedbadi.data.local.upcoming.UpcomingMovieKeysEntity
import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse

/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface UpcomingMoviesLocalDataSource {
    fun getAll(): PagingSource<Int, MovieEntity>
    suspend fun getRemoteKeysForMovieId(id: Long): UpcomingMovieKeysEntity?
    suspend fun cacheResponse(response: MoviePagedListResponse, pageKey: Int, isRefresh: Boolean)

}