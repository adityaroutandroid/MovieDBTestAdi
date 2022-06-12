package com.logituit.moviedbadi.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.util.Executors
import com.logituit.moviedbadi.data.local.MovieDatabase
import com.logituit.moviedbadi.data.repository.movies.MoviesLocalDataSource
import com.logituit.moviedbadi.data.repository.popular.PopularMoviesLocalDataSource
import com.logituit.moviedbadi.data.repository.upcoming.UpcomingMoviesLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context,
    ): MovieDatabase =
        Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
        )
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.mainThreadExecutor())
            .setQueryExecutor(Executors.mainThreadExecutor())
            .build()

    @Provides
    @Singleton
    fun providesPopularMoviesDataSource(
        database: MovieDatabase,
    ): PopularMoviesLocalDataSource = database.popularMoviesDao()

    @Provides
    @Singleton
    fun providesUpComingDataSource(
        database: MovieDatabase,
    ): UpcomingMoviesLocalDataSource = database.upcomingMoviesDao()

    @Provides
    @Singleton
    fun providesMovieDataSource(
        database: MovieDatabase,
    ): MoviesLocalDataSource = database.moviesDao()

    @Provides
    @Singleton
    fun providesMovieDao(database: MovieDatabase) = database.moviesDao()

    @Provides
    @Singleton
    fun providesUpComingDao(database: MovieDatabase) = database.upcomingMoviesDao()

    @Provides
    @Singleton
    fun providesPopularDao(database: MovieDatabase) = database.popularMoviesDao()

}