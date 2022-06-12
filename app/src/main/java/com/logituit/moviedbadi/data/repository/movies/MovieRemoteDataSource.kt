package com.logituit.moviedbadi.data.repository.movies

import com.logituit.moviedbadi.data.remote.model.MovieResponse
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface MovieRemoteDataSource {
    suspend fun getMovie(id: Long): MovieResponse
}