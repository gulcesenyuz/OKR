package com.example.okrprojectfinal.repository

import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.MovieResponse
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.data.service.MovieApi
import com.example.okrprojectfinal.data.utils.Credentials.API_KEY
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: MovieApi) {
    suspend fun getPopularMovies(): NetworkResult<MovieResponse> {
        val response = apiService.getPopularMovies(API_KEY)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error("Something went wrong")
            }
        } else {
            NetworkResult.Error("Something went wrong")
        }
    }

}