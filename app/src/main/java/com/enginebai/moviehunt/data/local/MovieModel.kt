package com.enginebai.moviehunt.data.local

import com.enginebai.moviehunt.data.remote.Genre

data class MovieModel(
    val id: String,
    val posterPath: String? = null,
    val title: String? = null,
    val voteAverage: Float? = null,
    val voteCount: Int? = null,
    val releaseDate: String? = null,
    val genreList: List<Genre>? = null,
    val runtime: Int? = null
)

