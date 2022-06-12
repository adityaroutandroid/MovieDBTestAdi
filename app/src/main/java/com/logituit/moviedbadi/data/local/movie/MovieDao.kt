package com.logituit.moviedbadi.data.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logituit.moviedbadi.data.repository.movies.MoviesLocalDataSource
import kotlinx.coroutines.flow.Flow
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@Dao
interface MovieDao : MoviesLocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(movies: MovieEntity)

    @Query("SELECT * FROM movies WHERE id=:id")
    override fun getMovieFlow(id: Long): Flow<MovieEntity?>
}