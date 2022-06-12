package com.logituit.moviedbadi.data.repository.upcoming

import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse

/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface UpcomingMoviesRemoteDataSource {
    suspend fun getUpcomingMovies(page: Int): MoviePagedListResponse

}