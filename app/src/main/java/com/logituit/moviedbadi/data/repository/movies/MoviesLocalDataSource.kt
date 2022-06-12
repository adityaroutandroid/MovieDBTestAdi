package com.logituit.moviedbadi.data.repository.movies

import com.logituit.moviedbadi.data.local.movie.MovieEntity
import kotlinx.coroutines.flow.Flow
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface MoviesLocalDataSource {
    fun getMovieFlow(id: Long): Flow<MovieEntity?>
    suspend fun insert(movies: MovieEntity)
}
