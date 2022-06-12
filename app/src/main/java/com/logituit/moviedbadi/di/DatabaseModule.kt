package com.logituit.moviedbadi.di

import android.content.Context
import androidx.room.Room
import com.logituit.moviedbadi.data.local.MovieDatabase
import com.logituit.moviedbadi.data.repository.movies.MoviesLocalDataSource
import com.logituit.moviedbadi.data.repository.popular.PopularMoviesLocalDataSource
import com.logituit.moviedbadi.data.repository.upcoming.UpcomingMoviesLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java, "movie.db"
        ).build()

    @Provides
    @Singleton
    fun providesPopularMoviesDataSource(
        database: MovieDatabase,
    ): PopularMoviesLocalDataSource = database.popularMoviesDao()

    @Provides
    @Singleton
    fun providesUpcomingMoviesDataSource(
        database: MovieDatabase,
    ): UpcomingMoviesLocalDataSource = database.upcomingMoviesDao()

    @Provides
    @Singleton
    fun providesMovieDataSource(
        database: MovieDatabase,
    ): MoviesLocalDataSource = database.moviesDao()
}