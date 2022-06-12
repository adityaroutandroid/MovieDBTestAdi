package com.logituit.moviedbadi.data.repository.upcoming

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

class UpcomingMovieRepository @Inject constructor(
    private val localDS: UpcomingMoviesLocalDataSource,
    private val remoteDS: UpcomingMoviesRemoteDataSource,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getUpcomingMovies() = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
        ),
        remoteMediator = UpcomingMoviesPagerMediator(remoteDS, localDS),
        pagingSourceFactory = { localDS.getAll() }
    ).flow.map { pagingData ->
        pagingData.map { it.toMovie() }
    }
}