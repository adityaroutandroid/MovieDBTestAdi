package com.logituit.moviedbadi.data.remote.model

import com.google.gson.annotations.SerializedName
import com.logituit.moviedbadi.data.local.movie.MovieEntity
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

data class MoviePagedListResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<MovieResponse>,
) {
    fun toMovieEntityList() = results.map {
        MovieEntity(
            id = it.id,
            title = it.title,
            overview = it.overview,
            rate = it.rate,
            genres = null,
            releaseDate = it.releaseDate,
            posterPath = it.posterPath,
            popularity = it.popularity,
        )
    }
}