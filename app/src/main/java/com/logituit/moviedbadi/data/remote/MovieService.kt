package com.logituit.moviedbadi.data.remote

import com.logituit.moviedbadi.data.remote.model.MovieResponse
import com.logituit.moviedbadi.data.repository.movies.MovieRemoteDataSource
import retrofit2.http.GET
import retrofit2.http.Path
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface MovieService : MovieRemoteDataSource {

    @GET("movie/{id}")
    override suspend fun getMovie(@Path("id") id: Long): MovieResponse
}