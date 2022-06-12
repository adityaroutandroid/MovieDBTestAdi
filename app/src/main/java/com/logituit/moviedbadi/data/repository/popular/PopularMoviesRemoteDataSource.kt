package com.logituit.moviedbadi.data.repository.popular

import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface PopularMoviesRemoteDataSource {
    suspend fun getPopularMovies(page: Int): MoviePagedListResponse
}
