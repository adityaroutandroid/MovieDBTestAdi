package com.logituit.moviedbadi.data.repository.popular

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.logituit.moviedbadi.data.remote.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.map
import javax.inject.Inject
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

class PopularMovieRepository @Inject constructor(
    private val localDS: PopularMoviesLocalDataSource,
    private val remoteDS: PopularMoviesRemoteDataSource,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularMovies() = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
        ),
        remoteMediator = PopularMoviesPagerMediator(remoteDS, localDS),
        pagingSourceFactory = { localDS.getAll() }
    ).flow.map { pagingData ->
        pagingData.map { it.toMovie() }
    }
}