package com.logituit.moviedbadi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.logituit.moviedbadi.data.repository.popular.PopularMovieRepository
import com.logituit.moviedbadi.data.repository.upcoming.UpcomingMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    popularMovieRepository: PopularMovieRepository,
    upcomingMovieRepository: UpcomingMovieRepository,
) : ViewModel() {

    val movies = popularMovieRepository.getPopularMovies().cachedIn(viewModelScope)
    val upcomingMovies = upcomingMovieRepository.getUpcomingMovies().cachedIn(viewModelScope)

}