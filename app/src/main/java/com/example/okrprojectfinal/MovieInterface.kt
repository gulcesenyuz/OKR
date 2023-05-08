package com.example.okrprojectfinal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieInterface {
    private val _popularMovies = MutableLiveData<List<MovieResponse>>()
    val popularMovies: LiveData<List<MovieResponse>> get() = _popularMovies
    fun getPopularMovies() {
        RetrofitHelper.api.getPopularMovies("69d66957eebff9666ea46bd464773cf0").enqueue(object :
            Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.body() != null) {
                    _popularMovies.value = response.body()!!.results
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }
        })
    }
}