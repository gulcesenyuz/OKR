package com.example.okrprojectfinal.data.model.response

import com.example.okrprojectfinal.data.model.Movie


data class MovieResponse(
    val total_results: Int,
    val results: List<Movie>,
)
