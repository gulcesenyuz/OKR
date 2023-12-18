package com.example.okrprojectfinal.data.model

import com.google.gson.annotations.SerializedName


data class Movie(
    val adult: Boolean?= null,
    @SerializedName("backdrop_path")
    val backdropPath: String?= null,
    val id: Int?= null,
    @SerializedName("original_language")
    val originalLanguage: String?= null,
    @SerializedName("original_title")
    val originalTitle: String?= null,
    val overview: String?= null,
    val popularity: String?= null,
    @SerializedName("poster_path")
    val posterPath: String?= null,
    @SerializedName("release_date")
    val releaseDate: String?= null,
    val title: String? = null,
    val video: Boolean? = false,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
)

