package com.logituit.moviedbadi.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@Parcelize
data class MovieData(
    val id: Long,
    val title: String,
    val overview: String,
    val genres: String?,
    val rate100: Int,
    val releaseDate: String?,
    val posterUrl: String?,
    val popularity: Float,
) : Parcelable