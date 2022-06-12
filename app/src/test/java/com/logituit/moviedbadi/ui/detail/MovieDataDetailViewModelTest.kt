package com.logituit.moviedbadi.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.logituit.moviedbadi.IITestCase
import com.logituit.moviedbadi.data.repository.favorites.FavoritesRepository
import com.logituit.moviedbadi.data.repository.movies.MovieRepository
import com.logituit.moviedbadi.domain.model.MovieData
import com.logituit.moviedbadi.domain.model.utils.State
import com.logituit.moviedbadi.domain.model.utils.loadingState
import com.logituit.moviedbadi.domain.model.utils.successState
import com.logituit.moviedbadi.movieDetail
import com.logituit.moviedbadi.movieId
import com.logituit.moviedbadi.unknownHostException
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDataDetailViewModelTest : IITestCase() {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val movieRepository: MovieRepository = mockk(relaxUnitFun = true)
    private val favoritesRepository: FavoritesRepository = mockk(relaxUnitFun = true)
    private val viewModel = MovieDetailViewModel(movieRepository, favoritesRepository)

    private val isFavoredObserver: Observer<Boolean> = mockk(relaxUnitFun = true)
    private val movieDataDetailObserver: Observer<State<MovieData?>> = mockk(relaxUnitFun = true)

    @Before
    override fun setUp() {
        super.setUp()
    }

    @After
    override fun tearDown() {
        confirmVerified(
            movieRepository,
            favoritesRepository,
            isFavoredObserver,
            movieDataDetailObserver
        )
        super.tearDown()
    }

    @Test
    fun `test loadMovie normal scenario`() {
        every { movieRepository.getMovie(movieId) } returns listOf(movieDetail).asFlow()

        viewModel.loadMovie(movieId).observeForever(movieDataDetailObserver)

        verifySequence {
            movieDataDetailObserver.onChanged(loadingState())
            movieRepository.getMovie(movieId)
            movieDataDetailObserver.onChanged(successState(movieDetail))
        }
    }

    @Test
    fun `test loadMovie failure scenario`() {
        every { movieRepository.getMovie(movieId) } returns flow<MovieData> { throw unknownHostException }

        viewModel.loadMovie(movieId).observeForever(movieDataDetailObserver)

        verifySequence {
            movieDataDetailObserver.onChanged(loadingState())
            movieRepository.getMovie(movieId)
            movieDataDetailObserver.onChanged(ofType(State.Failure::class))
        }
    }

    @Test
    fun `test toggleFavorite normal scenario`() {
        val isFavoredFlow = MutableStateFlow(false)
        every { favoritesRepository.isFavored(movieId) } returns isFavoredFlow
        coEvery { favoritesRepository.addToFavorites(movieId) } answers {
            isFavoredFlow.value = true
        }
        coEvery { favoritesRepository.removeFromFavorites(movieId) } answers {
            isFavoredFlow.value = false
        }

        viewModel.isFavoredStatus(movieId).observeForever(isFavoredObserver)

        viewModel.toggleFavorite(movieId)

        viewModel.toggleFavorite(movieId)

        coVerifySequence {
            favoritesRepository.isFavored(movieId)
            isFavoredObserver.onChanged(false)

            favoritesRepository.addToFavorites(movieId)
            isFavoredObserver.onChanged(true)

            favoritesRepository.removeFromFavorites(movieId)
            isFavoredObserver.onChanged(false)
        }
    }
}