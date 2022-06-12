package com.logituit.moviedbadi.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.logituit.moviedbadi.R
import com.logituit.moviedbadi.databinding.FragmentMovieDetailBinding
import com.logituit.moviedbadi.domain.model.MovieData
import com.logituit.moviedbadi.domain.model.utils.IIError
import com.logituit.moviedbadi.domain.model.utils.State
import com.logituit.moviedbadi.domain.model.utils.getHumanReadableText
import com.logituit.moviedbadi.ui.common.base.BaseBottomSheetDialogFragment
import com.logituit.moviedbadi.utils.doOnFinished
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

@AndroidEntryPoint
class MovieDetailFragment : BaseBottomSheetDialogFragment<FragmentMovieDetailBinding>() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentMovieDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        loadData()
    }

    private fun loadData() = viewModel.apply {
        loadMovie(args.movieData.id).observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> state.value?.let { populateUi(it) }
                is State.Loading -> showLoading()
                is State.Failure -> populateError(state.error)
                else -> throw IllegalStateException("bad state for load movie")
            }
        }
    }

    private fun initUI() = with(binding) {
        val movie = args.movieData

      /*  sharedElementEnterTransition = DetailsTransition()
        sharedElementReturnTransition = DetailsTransition()
        postponeEnterTransition()

        posterImageView.transitionName = posterTransitionName(movie.id)
        titleTextView.transitionName = titleTransitionName(movie.id)
        dateTextView.transitionName = dateTransitionName(movie.id)*/

        titleTextView.text = movie.title
        dateTextView.text = movie.releaseDate

        Glide.with(requireContext())
            .load(movie.posterUrl)
            .dontAnimate()
            .placeholder(R.drawable.ic_place_holder_24dp)
            .doOnFinished { startPostponedEnterTransition() }
            .into(posterImageView)

        loadStateView.setOnRetryListener { loadData() }
    }

    private fun populateUi(movieData: MovieData) = with(binding) {

        loadStateView.hide()

        Glide.with(requireContext())
            .load(movieData.posterUrl)
            .dontAnimate()
            .placeholder(R.drawable.ic_place_holder_24dp)
            .doOnFinished { startPostponedEnterTransition() }
            .into(posterImageView)

        titleTextView.text = movieData.title
        dateTextView.text = movieData.releaseDate
        overviewTextView.text = movieData.overview

        loadStateView.hideErrorMessage()
    }


    private fun populateError(error: IIError) = with(binding) {
        startPostponedEnterTransition()
        loadStateView.showErrorMessage(error.getHumanReadableText(requireContext()))
    }

    private fun showLoading() = with(binding) {
        loadStateView.isLoadingVisible = true
        loadStateView.hideErrorMessage()
    }
}