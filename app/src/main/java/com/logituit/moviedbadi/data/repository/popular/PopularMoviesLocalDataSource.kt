package com.logituit.moviedbadi.data.repository.popular

import androidx.paging.PagingSource
import com.logituit.moviedbadi.data.local.movie.MovieEntity
import com.logituit.moviedbadi.data.local.popular.PopularMovieKeysEntity
import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface PopularMoviesLocalDataSource {
    fun getAll(): PagingSource<Int, MovieEntity>
    suspend fun getRemoteKeysForMovieId(id: Long): PopularMovieKeysEntity?
    suspend fun cacheResponse(response: MoviePagedListResponse, pageKey: Int, isRefresh: Boolean)
}
