package com.logituit.moviedbadi.data.remote.model

import com.google.gson.annotations.SerializedName
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

data class GenreResponse(
    @SerializedName("name") val name: String,
) {
    override fun toString() = name
}