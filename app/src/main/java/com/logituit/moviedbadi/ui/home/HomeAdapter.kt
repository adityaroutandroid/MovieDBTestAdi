package com.logituit.moviedbadi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.logituit.moviedbadi.R
import com.logituit.moviedbadi.databinding.ListItemHomeBinding
import com.logituit.moviedbadi.domain.model.MovieData
import com.logituit.moviedbadi.utils.posterTransitionName
import com.logituit.moviedbadi.utils.titleTransitionName
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

class HomeAdapter(
    private val onItemClick: (MovieData, ImageView, TextView) -> Unit,
) : PagingDataAdapter<MovieData, HomeItemViewHolder>(MOVIE_Data_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder =
        HomeItemViewHolder.create(parent, onItemClick)


    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    companion object {
        private val MOVIE_Data_COMPARATOR = object : DiffUtil.ItemCallback<MovieData>() {
            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean =
                oldItem == newItem
        }
    }
}

class HomeItemViewHolder(
    private val binding: ListItemHomeBinding,
    private val onItemClick: (MovieData, ImageView, TextView) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var _movieData: MovieData? = null

    init {
        with(binding) {
            root.setOnClickListener {
                _movieData?.let {
                    onItemClick.invoke(
                        it,
                        posterImageView,
                        titleTextView)
                }
            }
        }
    }

    fun onBind(movieData: MovieData) = with(binding) {
        _movieData = movieData

        titleTextView.text = movieData.title

        posterImageView.transitionName = posterTransitionName(movieData.id)
        titleTextView.transitionName = titleTransitionName(movieData.id)

        Glide.with(root.context)
            .load(movieData.posterUrl)
            .placeholder(R.drawable.ic_place_holder_24dp)
            .into(posterImageView)
    }

    companion object {
        fun create(parent: ViewGroup, onItemClick: (MovieData, ImageView, TextView) -> Unit) =
            HomeItemViewHolder(
                ListItemHomeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                onItemClick
            )
    }
}