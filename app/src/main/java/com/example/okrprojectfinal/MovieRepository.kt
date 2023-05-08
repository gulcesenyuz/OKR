package com.example.okrprojectfinal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository constructor( private val service: MovieInterface)
{
    private val _popularMovies = MutableLiveData<List<MovieResponse>>()
    val popularMovies: LiveData<List<MovieResponse>> get() = _popularMovies
    private var movieLiveData = MutableLiveData<List<MovieResponse>>()

    fun getPopularMovies() {
       service.getPopularMovies()
    }
    fun observeMovieLiveData() : LiveData<List<MovieResponse>> {
        return service.popularMovies
    }
}