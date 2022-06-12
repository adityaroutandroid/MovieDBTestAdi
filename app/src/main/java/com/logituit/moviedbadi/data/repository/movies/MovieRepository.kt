package com.logituit.moviedbadi.data.repository.movies

import com.logituit.moviedbadi.domain.model.MovieData
import com.logituit.moviedbadi.domain.model.utils.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

class MovieRepository @Inject constructor(
    private val local: MoviesLocalDataSource,
    private val remote: MovieRemoteDataSource,
) {

    fun getMovie(id: Long): Flow<MovieData?> =
        local.getMovieFlow(id).transform { localMovie ->

            emit(localMovie?.toMovie())

            try {
                if (localMovie == null || !localMovie.isDetailLoaded()) {
                    remote.getMovie(id).let {
                        local.insert(it.toMovieEntity())
                    }
                }
            } catch (e: UnknownHostException) {
                throw NetworkError(e)
            } catch (e: HttpException) {
                throw NetworkError(e)
            }
        }
}