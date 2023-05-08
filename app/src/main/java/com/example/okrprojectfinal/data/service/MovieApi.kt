package com.example.okrprojectfinal.data.service

import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.MovieResponse
import com.example.okrprojectfinal.data.model.response.NetworkResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("popular?")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String
    ): Response<MovieResponse>
}

