package com.logituit.moviedbadi.data.repository.upcoming

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bumptech.glide.load.HttpException
import com.logituit.moviedbadi.data.local.movie.MovieEntity
import com.logituit.moviedbadi.data.local.upcoming.UpcomingMovieKeysEntity
import com.logituit.moviedbadi.data.remote.STARTING_PAGE_INDEX
import com.logituit.moviedbadi.domain.model.utils.NetworkError
import com.logituit.moviedbadi.domain.model.utils.UnknownError
import timber.log.Timber
import java.io.IOException
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@OptIn(ExperimentalPagingApi::class)
class UpcomingMoviesPagerMediator(
    private val remote: UpcomingMoviesRemoteDataSource,
    private val local: UpcomingMoviesLocalDataSource,
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        Timber.v("loadType: $loadType, state: $state")
        val pageKey = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                remoteKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
        }
        Timber.v("page: $pageKey")

        return try {
            val apiResponse = remote.getUpcomingMovies(pageKey)
            Timber.v("apiResponseResults ${apiResponse.results.size}")

            local.cacheResponse(apiResponse, pageKey, loadType == LoadType.REFRESH)
            MediatorResult.Success(endOfPaginationReached = pageKey >= apiResponse.totalPages)
        } catch (exception: IOException) {
            MediatorResult.Error(UnknownError(exception))
        } catch (exception: HttpException) {
            MediatorResult.Error(NetworkError(exception))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): UpcomingMovieKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie -> local.getRemoteKeysForMovieId(movie.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): UpcomingMovieKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie -> local.getRemoteKeysForMovieId(movie.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): UpcomingMovieKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
                ?.id?.let { movieId -> local.getRemoteKeysForMovieId(movieId) }
        }
    }
}