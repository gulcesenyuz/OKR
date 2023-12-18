package com.example.okrprojectfinal

import com.example.okrprojectfinal.data.model.Movie

object MockData {
    private fun mockMovie() =
        Movie(
            adult = true,
            backdropPath = null,
            id = 419704,
            title = "Ad Astra",
            originalLanguage = null,
            releaseDate = "2019-09-17",
            voteAverage = 6.0,
            originalTitle = null,
            popularity = null,
            posterPath = null,
            video = null,
            voteCount = null,
            overview = null
        )

    fun mockMovieList() = listOf(mockMovie())
    fun mockMoviesList() = listOf(mockMovie(), mockMovie(), mockMovie())

}
