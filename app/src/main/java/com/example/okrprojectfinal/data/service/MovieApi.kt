package com.example.okrprojectfinal.data.service

import com.example.okrprojectfinal.data.model.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("popular?")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String
    ): MovieResponse
}
