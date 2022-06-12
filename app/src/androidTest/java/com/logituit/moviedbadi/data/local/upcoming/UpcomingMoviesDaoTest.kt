package com.logituit.moviedbadi.data.local.upcoming

import androidx.paging.PagingSource
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.logituit.moviedbadi.utils.*
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@MediumTest
class UpcomingMoviesDaoTest : IITestCase() {

    @Inject
    lateinit var upcomingMoviesDao: UpcomingMoviesDao

    @Before
    override fun setUp() = super.setUp()

    @Test
    fun insertAndRetrieveTest() = td.runBlockingTest {

        val latch = CountDownLatch(1)
        val job = testScope.launch {
            upcomingMoviesDao.cacheResponse(page1Response, 1, true)
            upcomingMoviesDao.cacheResponse(page2Response, 2, false)
//
            val pagedSource = upcomingMoviesDao.getAll()

            var page = pagedSource.load(
                PagingSource.LoadParams.Refresh(0, 1, false)
            )

            assertThat(page).isEqualTo(
                PagingSource.LoadResult.Page(
                    data = listOf(upcomingMoviesDao),
                    prevKey = null,
                    nextKey = 1,
                    itemsBefore = 0,
                    itemsAfter = 1
                )
            )

            page = pagedSource.load(
                PagingSource.LoadParams.Append(1, 1, false)
            )

            assertThat(page).isEqualTo(
                PagingSource.LoadResult.Page(
                    data = listOf(upComingMovie2),
                    prevKey = 1,
                    nextKey = 2,
                    itemsBefore = Int.MIN_VALUE,
                    itemsAfter = Int.MIN_VALUE
                )
            )
            latch.countDown()
        }
        @Suppress("BlockingMethodInNonBlockingContext")
        latch.await(LATCH_AWAIT_TIMEOUT, TimeUnit.SECONDS)
        throwScopeExceptions()
        job.cancel()
    }
}