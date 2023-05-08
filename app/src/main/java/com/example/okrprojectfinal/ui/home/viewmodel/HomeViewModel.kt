package com.example.okrprojectfinal.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private var _movieResponse = MutableLiveData<NetworkResult<List<Movie>>>()
    val movieResponse: LiveData<NetworkResult<List<Movie>>> = _movieResponse

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovies().collect {
                _movieResponse.postValue(it)
            }
        }
    }
}
