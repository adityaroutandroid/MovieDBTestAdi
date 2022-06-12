package com.logituit.moviedbadi

import com.google.gson.Gson
import com.logituit.moviedbadi.data.remote.model.MoviePagedListResponse
import com.logituit.moviedbadi.data.remote.model.MovieResponse
import java.net.UnknownHostException
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

val unknownHostException = UnknownHostException()

val pagedListResponse: MoviePagedListResponse =
    Gson().fromJson(pagedListJson, MoviePagedListResponse::class.java)
val movieEntityList = pagedListResponse.toMovieEntityList()

val movieItemEntity = movieEntityList[0]
val movieItem = movieItemEntity.toMovie()


val movieDetailResponse: MovieResponse = Gson().fromJson(movieDetailJson, MovieResponse::class.java)
val movieDetailEntity = movieDetailResponse.toMovieEntity()
val movieDetail = movieDetailEntity.toMovie()
val movieId = movieDetailResponse.id

