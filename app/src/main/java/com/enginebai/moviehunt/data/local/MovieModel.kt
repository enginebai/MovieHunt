package com.enginebai.moviehunt.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.enginebai.moviehunt.BuildConfig
import com.enginebai.moviehunt.data.remote.Genre
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.utils.format
import com.enginebai.moviehunt.utils.formatHourMinutes

const val PLACEHOLDER = "--"

@Entity(
	tableName = "movie_list",
	primaryKeys = ["category", "movie_id"],
	foreignKeys = [ForeignKey(
		entity = MovieModel::class,
		parentColumns = ["id"],
		childColumns = ["movie_id"],
		onDelete = ForeignKey.CASCADE,
		onUpdate = ForeignKey.CASCADE
	)]
)
data class MovieListId(
	val category: MovieCategory,
	@ColumnInfo(name = "movie_id")
	val movieId: String
)

@Entity(tableName = "movie")
data class MovieModel(
	@PrimaryKey
    val id: String,
	@ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    val title: String? = null,
	@ColumnInfo(name = "vote_average")
    val voteAverage: Float? = null,
	@ColumnInfo(name = "vote_count")
    val voteCount: Int? = null,
    val overview: String? = null,
	@ColumnInfo(name = "release_date")
    val releaseDate: String? = null,
	@ColumnInfo(name = "genres")
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
fun MovieModel.displayOverview(): String = this.overview ?: PLACEHOLDER