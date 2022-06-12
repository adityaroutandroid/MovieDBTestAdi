package com.logituit.moviedbadi.data.local.common

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.logituit.moviedbadi.data.local.movie.MovieEntity
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

interface CommonMovieListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieEntity>)
}