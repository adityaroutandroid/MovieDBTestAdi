package com.logituit.moviedbadi.data.remote.upcoming

import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse
import com.logituit.moviedbadi.data.repository.upcoming.UpcomingMoviesRemoteDataSource
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

 interface UpcomingMoviesService : UpcomingMoviesRemoteDataSource{
     @GET("movie/upcoming")
     override suspend fun getUpcomingMovies(@Query("page")page: Int): MoviePagedListResponse
}