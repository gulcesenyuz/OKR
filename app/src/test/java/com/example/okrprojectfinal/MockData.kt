package com.example.okrprojectfinal

import com.example.okrprojectfinal.data.model.Movie

object MockData {
    private fun mockMovie() =
        Movie(
            adult = true,
            backdrop_path = null,
            id = 419704,
            title = "Ad Astra",
            original_language = null,
            release_date = "2019-09-17",
            vote_average = 6.0,
            original_title = null,
            popularity = null,
            poster_path = null,
            video = null,
            vote_count = null,
            overview = null
        )

    fun mockMovieList() = listOf(mockMovie())
    fun mockMoviesList() = listOf(mockMovie(), mockMovie(), mockMovie())

}
