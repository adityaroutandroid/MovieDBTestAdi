package com.logituit.moviedbadi.di

import com.logituit.moviedbadi.BuildConfig
import com.logituit.moviedbadi.data.remote.API_BASE_URL
import com.logituit.moviedbadi.data.remote.MovieService
import com.logituit.moviedbadi.data.remote.popular.PopularMoviesService
import com.logituit.moviedbadi.data.remote.upcoming.UpcomingMoviesService
import com.logituit.moviedbadi.data.repository.movies.MovieRemoteDataSource
import com.logituit.moviedbadi.data.repository.popular.PopularMoviesRemoteDataSource
import com.logituit.moviedbadi.data.repository.upcoming.UpcomingMoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val apiKeyInterceptor: (Interceptor.Chain) -> Response = { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val newUrl: HttpUrl = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }

        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .addInterceptor(apiKeyInterceptor)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun providesMovieRemoteDataSource(
        retrofit: Retrofit,
    ): MovieRemoteDataSource = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun providesPopularMoviesRemoteDataSource(
        retrofit: Retrofit,
    ): PopularMoviesRemoteDataSource = retrofit.create(PopularMoviesService::class.java)

    @Provides
    @Singleton
    fun providesUpcomingMoviesRemoteDataSource(
        retrofit: Retrofit,
    ): UpcomingMoviesRemoteDataSource = retrofit.create(UpcomingMoviesService::class.java)

   }