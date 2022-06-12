package com.logituit.moviedbadi.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.logituit.moviedbadi.data.repository.movies.MovieRepository
import com.logituit.moviedbadi.domain.model.utils.withStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    fun loadMovie(id: Long) = withStates(200) { movieRepository.getMovie(id) }
        .asLiveData(viewModelScope.coroutineContext)

}