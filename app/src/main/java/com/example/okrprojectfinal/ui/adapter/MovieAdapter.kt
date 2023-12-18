package com.example.okrprojectfinal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.MovieResponse
import com.example.okrprojectfinal.data.utils.Credentials.IMG_BASE
import com.example.okrprojectfinal.databinding.MovieLayoutBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies = mutableListOf<Movie>()
    private var clickInterface: ClickInterface<Movie>? = null

    fun updateMovies(movies: MovieResponse) {
        this.movies = movies.results.toMutableList()
        notifyItemRangeInserted(0, movies.results.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.view.tvTitle.text = movie.title
        holder.view.rvYear.text = "Year : ${movie.releaseDate}"
        holder.view.rvRating.text = "Rating : ${movie.voteAverage}"
        Glide
            .with(holder.view.imgMovieImage.context)
            .load(IMG_BASE + movie.posterPath)
            .centerCrop()
            .into(holder.view.imgMovieImage)
        holder.view.movieCard.setOnClickListener {
            clickInterface?.onClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setItemClick(clickInterface: ClickInterface<Movie>) {
        this.clickInterface = clickInterface
    }

    class MovieViewHolder(val view: MovieLayoutBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
}