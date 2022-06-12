package com.logituit.moviedbadi.data.remote.popular

import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse
import com.logituit.moviedbadi.data.repository.popular.PopularMoviesRemoteDataSource
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface PopularMoviesService : PopularMoviesRemoteDataSource {

    @GET("discover/movie?sort_by=popularity.desc")
    override suspend fun getPopularMovies(@Query("page") page: Int): MoviePagedListResponse
}