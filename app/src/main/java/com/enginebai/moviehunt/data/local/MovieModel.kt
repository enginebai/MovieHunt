package com.enginebai.moviehunt.data.local

import com.enginebai.moviehunt.BuildConfig
import com.enginebai.moviehunt.data.remote.Genre
import com.enginebai.moviehunt.utils.format
import com.enginebai.moviehunt.utils.formatHourMinutes

const val PLACEHOLDER = "--"

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

fun MovieModel.getPosterUrl(): String = "${BuildConfig.IMAGE_API_KEY}w500/${this.posterPath}"
fun MovieModel.displayTitle(): String = this.title ?: PLACEHOLDER
fun MovieModel.display5StarsRating(): Float = this.voteAverage?.div(2) ?: 0.0f
fun MovieModel.displayVoteCount(): String = this.voteCount?.format() ?: PLACEHOLDER
// scale from 0-10 to 0-100%
fun MovieModel.displayVotePercentage(): String = "${this.voteAverage?.times(10) ?: PLACEHOLDER}%"

fun MovieModel.displayDuration(): String = this.runtime?.formatHourMinutes() ?: PLACEHOLDER
fun MovieModel.displayReleaseDate(): String = this.releaseDate ?: PLACEHOLDER