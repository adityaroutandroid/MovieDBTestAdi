package com.logituit.moviedbadi.utils

import com.google.gson.Gson
import com.logituit.moviedbadi.data.local.popular.PopularMovieKeysEntity
import com.logituit.moviedbadi.data.local.upcoming.UpcomingMovieKeysEntity
import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse
import com.logituit.moviedbadi.data.remote.model.MovieResponse
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

val pagedListResponse: MoviePagedListResponse =
    Gson().fromJson(pagedListJson, MoviePagedListResponse::class.java)
val page1Response: MoviePagedListResponse =
    Gson().fromJson(page1Json, MoviePagedListResponse::class.java)
val page2Response: MoviePagedListResponse =
    Gson().fromJson(page2Json, MoviePagedListResponse::class.java)

val movieItemEntity = page1Response.toMovieEntityList()[0]
val movieItemEntity2 = page2Response.toMovieEntityList()[0]
val movieItem = movieItemEntity.toMovie()


val popularMovie = PopularMovieKeysEntity(movieItemEntity.id, null, 1, 2)
val popularMovie2 = PopularMovieKeysEntity(movieItemEntity2.id, 1, 2, null)

val upComingMovie = UpcomingMovieKeysEntity(movieItemEntity.id, null, 1, 2)
val upComingMovie2 = PopularMovieKeysEntity(movieItemEntity2.id, 1, 2, null)

val movieDetailResponse: MovieResponse = Gson().fromJson(movieDetailJson, MovieResponse::class.java)
val movieDetailEntity = movieDetailResponse.toMovieEntity()
val movieDetail = movieDetailEntity.toMovie()
val movieId = movieDetailResponse.id

const val LATCH_AWAIT_TIMEOUT = 1L