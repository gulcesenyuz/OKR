package com.example.okrprojectfinal.repository

import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.data.service.MovieApi
import com.example.okrprojectfinal.data.utils.Credentials.API_KEY
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: MovieApi) {
    suspend fun getPopularMovies() = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getPopularMovies(API_KEY)
        emit(NetworkResult.Success(response.results))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}