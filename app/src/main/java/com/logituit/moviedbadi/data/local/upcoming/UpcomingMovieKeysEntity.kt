package com.logituit.moviedbadi.data.local.upcoming

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@Entity(tableName = "upcoming_movies_keys")
data class UpcomingMovieKeysEntity(
    @PrimaryKey @field:SerializedName("movie_id") val movieId: Long,
    @field:SerializedName("prev_key") val prevKey: Int?,
    @field:SerializedName("cur_key") val curKey: Int,
    @field:SerializedName("next_key") val nextKey: Int?,
)