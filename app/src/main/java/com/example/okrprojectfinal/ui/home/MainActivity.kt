package com.example.okrprojectfinal.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.databinding.ActivityMainBinding
import com.example.okrprojectfinal.ui.adapter.MovieAdapter
import com.example.okrprojectfinal.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var movieAdapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()

        mainViewModel.movieResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false
                }

                is NetworkResult.Success -> {
                    movieAdapter.updateMovies(it.data)
                    binding.progressbar.isVisible = false
                }
            }
        }
    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = movieAdapter
        }
    }
}
