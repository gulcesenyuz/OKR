package com.example.okrprojectfinal.data.model.response

import com.example.okrprojectfinal.data.model.Movie


data class MovieResponse(
    val results: List<Movie>,
    val errorMessage: String
)
