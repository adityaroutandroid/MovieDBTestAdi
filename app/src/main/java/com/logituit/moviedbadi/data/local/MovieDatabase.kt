package com.logituit.moviedbadi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.logituit.moviedbadi.data.local.movie.MovieDao
import com.logituit.moviedbadi.data.local.movie.MovieEntity
import com.logituit.moviedbadi.data.local.popular.PopularMovieKeysEntity
import com.logituit.moviedbadi.data.local.popular.PopularMoviesDao
import com.logituit.moviedbadi.data.local.upcoming.UpcomingMovieKeysEntity
import com.logituit.moviedbadi.data.local.upcoming.UpcomingMoviesDao
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@Database(
    entities = [
        MovieEntity::class,
        PopularMovieKeysEntity::class,
        UpcomingMovieKeysEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao
}