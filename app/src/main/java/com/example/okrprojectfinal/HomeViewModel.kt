package com.example.okrprojectfinal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class HomeViewModel constructor(private val repository: MovieRepository)  : ViewModel() {
    fun getPopularMovies() {
        repository.getPopularMovies()
    }

    fun observeMovieLiveData(): LiveData<List<MovieResponse>> {
        return repository.observeMovieLiveData()
    }
}
