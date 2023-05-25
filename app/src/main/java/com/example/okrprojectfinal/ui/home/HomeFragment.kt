package com.example.okrprojectfinal.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
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
        homeViewModel.movieResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Loading->{
                    // binding.progressbar.isVisible = it.isLoading

                }
                is NetworkResult.Success -> {
                    Log.d("CHEEZ", it.data.toString())
                    prepareRecyclerView()
                    it.data?.let { it1 -> movieAdapter.updateMovies(it1) }
                    binding.progressbar.isVisible = false
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
}