package com.logituit.moviedbadi.data.local.movie

import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.logituit.moviedbadi.utils.IITestCase
import com.logituit.moviedbadi.utils.movieId
import com.logituit.moviedbadi.utils.movieItemEntity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@MediumTest
class MovieDataDaoTest : IITestCase() {

    @Inject
    lateinit var movieDao: MovieDao

    @Before
    override fun setUp() = super.setUp()

    @Test
    fun testInsertAndRetrieveMovie() = td.runBlockingTest {
        movieDao.insert(movieItemEntity)

        val movieList = mutableListOf<MovieEntity?>()
        val job = launch {
            movieDao.getMovieFlow(movieId)
                .collect { movieList.add(it) }
        }
        job.cancel()

        assertThat(movieList).isEqualTo(listOf(movieItemEntity))
    }
}