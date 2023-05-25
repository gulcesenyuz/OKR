package com.example.okrprojectfinal.data.service

import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular?")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MovieResponse>
}

