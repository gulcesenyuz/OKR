package com.example.okrprojectfinal.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.MovieResponse
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private var _movieResponse = MutableLiveData<NetworkResult<MovieResponse>>()
    val movieResponse: LiveData<NetworkResult<MovieResponse>>
        get() = _movieResponse

    init {
        getPopularMovies()
    }
    fun getPopularMovies() {
        viewModelScope.launch {
            val result = repository.getPopularMovies()
            _movieResponse.postValue(result)
        }
    }
}
