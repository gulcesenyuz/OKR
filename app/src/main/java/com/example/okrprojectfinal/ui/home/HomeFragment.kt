package com.example.okrprojectfinal.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.databinding.FragmentPopularMoviesBinding
import com.example.okrprojectfinal.ui.adapter.MovieAdapter
import com.example.okrprojectfinal.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentPopularMoviesBinding
    private val homeViewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularMoviesBinding.inflate(layoutInflater)
        val view = binding.root
        prepareRecyclerView()
        homeViewModel.movieResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                    // binding.progressbar.isVisible = it.isLoading
                }

                is NetworkResult.Success -> {
                    binding.progressbar.isVisible = false
                    it.data?.let { it -> createMovieDatabase(it.results) }
                }

                is NetworkResult.Error -> {
                    binding.progressbar.isVisible = false

                }
            }
        })
        return view
    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = movieAdapter
        }
    }

    //Method 1: Snapshot
    // private fun fetchMovieListFromDatabase() {
    //     homeViewModel.fetchPopularMovies()
    //  }

    private fun createMovieDatabase(data: List<Movie>) {
        homeViewModel.storeMovieToDatabase(data)
        homeViewModel.isSuccess.observe(viewLifecycleOwner) { data ->
            if (data) {
                Toast.makeText(context, "success", Toast.LENGTH_LONG).show()
                showOptionDialog();
            } else {
                Toast.makeText(context, "failure", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showOptionDialog() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Your data stored, retrieve data from Firestore")
                .setPositiveButton("Start") { _, _ ->
                    homeViewModel.fetchPopularMovies()
                }
                .setNegativeButton("Cancel") { _, _ ->
                    // User cancelled the dialog.
                }
            builder.create().show()
        } ?: throw IllegalStateException("Activity cannot be null")
        displayMovies()
    }

    private fun displayMovies() {
        homeViewModel.movieLiveData.observe(viewLifecycleOwner) { data ->
            data?.let {
                movieAdapter.updateMovies(data)
            }
        }
    }
}
