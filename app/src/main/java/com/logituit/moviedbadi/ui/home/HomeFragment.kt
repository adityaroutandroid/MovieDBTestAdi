package com.logituit.moviedbadi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.logituit.moviedbadi.R
import com.logituit.moviedbadi.databinding.FragmentHomeBinding
import com.logituit.moviedbadi.domain.model.MovieData
import com.logituit.moviedbadi.ui.common.base.BaseFragment
import com.logituit.moviedbadi.ui.common.loadstate.ListLoadStateAdapter
import com.logituit.moviedbadi.utils.ViewLifecycleDelegate
import com.logituit.moviedbadi.utils.listenOnLoadStates
import com.logituit.moviedbadi.utils.navigateSafe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()

    private val homeAdapter by ViewLifecycleDelegate { HomeAdapter(::onMovieClicked) }

    private val upcomingAdapter by ViewLifecycleDelegate { HomeAdapter(::onMovieClicked) }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        listenOnMoviesPagedData()
        listenOnPagerLoadStates()
        listenOnUpcomingMoviesPagedData()
    }

    private fun listenOnMoviesPagedData() = viewLifecycleOwner.lifecycleScope.launch {
        homeViewModel.movies.collectLatest { homeAdapter.submitData(it) }
    }
    private fun listenOnUpcomingMoviesPagedData() = viewLifecycleOwner.lifecycleScope.launch {
        homeViewModel.upcomingMovies.collectLatest { upcomingAdapter.submitData(it) }
    }

    private fun listenOnPagerLoadStates() = viewLifecycleOwner.lifecycleScope.launch {
        with(binding) {
            homeAdapter.listenOnLoadStates(
                popularRecyclerView,
                popularLoadStateView,
                { homeAdapter.itemCount == 0 },
                getString(R.string.no_popular_movies)
            )
            upcomingAdapter.listenOnLoadStates(
                upcomingRecyclerView,
                upcomingLoadStateView,
                {upcomingAdapter.itemCount==0},
                getString(R.string.no_upcoming_movies)
            )
        }
    }

    private fun initUI() = with(binding) {
        Timber.v("before postponeEnterTransition")
        postponeEnterTransition()
        upcomingRecyclerView.apply {
            adapter = upcomingAdapter.withLoadStateFooter(
                footer = ListLoadStateAdapter{upcomingAdapter.retry()}
            )
        }
        popularRecyclerView.apply {
            adapter = homeAdapter.withLoadStateFooter(
                footer = ListLoadStateAdapter { homeAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

            popularRecyclerView.layoutManager =layoutManager
            doOnPreDraw { startPostponedEnterTransition(); Timber.v("startPostponedEnterTransition called") }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                private var dySum = 0

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE,
                        RecyclerView.SCROLL_STATE_SETTLING,
                        -> {
                            popularPageTitle.isVisible = (dySum <= 0)
                            dySum = 0
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    dySum += dy
                }
            })
        }
        upcomingRecyclerView.apply {
            adapter = upcomingAdapter.withLoadStateFooter(
                footer = ListLoadStateAdapter { upcomingAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            doOnPreDraw { startPostponedEnterTransition(); Timber.v("startPostponedEnterTransition called") }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                private var dySum = 0

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE,
                        RecyclerView.SCROLL_STATE_SETTLING,
                        -> {
                            upcomingPageTitle.isVisible = (dySum <= 0)
                            dySum = 0
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    dySum += dy
                }
            })
        }
    }

    private fun onMovieClicked(
        movieData: MovieData,
        posterImageView: ImageView,
        titleTextView: TextView,
    ) {
        findNavController().navigateSafe(
            HomeFragmentDirections.actionNavigationHomeToMovieDetails(movieData))
    }
}