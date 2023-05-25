package com.example.okrprojectfinal.ui.search

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
import com.example.okrprojectfinal.databinding.FragmentSearchBinding
import com.example.okrprojectfinal.ui.adapter.MovieAdapter
import com.example.okrprojectfinal.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel by viewModels<SearchViewModel>()

    @Inject
    lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        val view = binding.root
        binding.progressbar.visibility = View.GONE
        binding.btnSearch.setOnClickListener {
            Log.d("TEST", " binding.btnSearch. SearchScreenFragment")
            searchMovie(binding.etSearch.text.toString(), 1)
        }
        searchViewModel.movieResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Loading->{
                    binding.progressbar.isVisible = true
                }
                is NetworkResult.Success -> {
                    Log.d("CHEEZ", it.data.toString())
                    prepareRecyclerView()
                    it.data?.let {
                            it1 -> movieAdapter.updateMovies(it1)
                    }
                    binding.progressbar.isVisible = false
                }
                is NetworkResult.Error -> {
                    binding.progressbar.isVisible = false

                }
            }
        })
        return view
    }
    private fun searchMovie(query: String, pageNumber: Int) {
        searchViewModel.getSearchResultMovies(query, pageNumber)
    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvSearchResult.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = movieAdapter
        }
    }
}
