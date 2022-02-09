package com.enginebai.moviehunt.data.local

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.enginebai.moviehunt.data.remote.Genre
import com.enginebai.moviehunt.data.remote.ImageApi
import com.enginebai.moviehunt.data.remote.ImageSize
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.ui.widgets.MovieLandscapeWidget
import com.enginebai.moviehunt.ui.widgets.MoviePortraitHolder_
import com.enginebai.moviehunt.ui.widgets.MoviePortraitWidget
import com.enginebai.moviehunt.ui.widgets.MovieShowcaseWidget
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import com.enginebai.moviehunt.utils.format
import com.enginebai.moviehunt.utils.formatHourMinutes
import java.util.*

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
    val movieId: String,
    val position: Long
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
    val releaseDate: Calendar? = null,
    @ColumnInfo(name = "genres")
    val genreList: List<Genre>? = null,
    val runtime: Int? = null,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,
)

fun MovieModel.displayTitle(): String = this.title ?: PLACEHOLDER
fun MovieModel.display5StarsRating(): Float = this.voteAverage?.div(2) ?: 0.0f
fun MovieModel.displayVoteCount(): String = this.voteCount?.format() ?: PLACEHOLDER
fun MovieModel.displayDuration(): String = this.runtime?.formatHourMinutes() ?: PLACEHOLDER
fun MovieModel.displayReleaseDate(): String = this.releaseDate?.format() ?: PLACEHOLDER
fun MovieModel.displayOverview(): String = this.overview ?: PLACEHOLDER

fun MovieModel.toPortraitHolder(): MoviePortraitHolder_ = MoviePortraitHolder_()
    .movieId(this.id)
    .posterUrl(ImageApi.getFullUrl(this.posterPath, ImageSize.W500))
    .movieName(this.displayTitle())
    .rating(this.display5StarsRating())
    .ratingTotalCountText(this.displayVoteCount())
    .genre(this.genreList?.firstOrNull()?.name)
    .releaseYear(this.releaseDate?.get(Calendar.YEAR))

@Composable
fun MovieModel.PortraitWidget(onClick: (String) -> Unit) {
    MoviePortraitWidget(
        movieId = this.id,
        posterUrl = ImageApi.getFullUrl(this.posterPath, ImageSize.W500),
        movieName = this.displayTitle(),
        rating = this.display5StarsRating(),
        ratingTotalCountText = "(${this.displayVoteCount()})",
        genre = this.genreList?.firstOrNull()?.name,
        releaseYear = this.releaseDate?.get(Calendar.YEAR),
        onClickListener = onClick
    )
}

@Composable
fun MovieModel.LandscapeWidget(onClick: (String) -> Unit) {
    MovieLandscapeWidget(
        movieId = id,
        imagePoster = ImageApi.getFullUrl(posterPath, ImageSize.W500),
        textTitle = displayTitle(),
        rating = display5StarsRating(),
        ratingTotalCountText = displayVoteCount(),
        genre = genreList?.map { it.name }?.joinToString(),
        releaseDateText = releaseDate?.format(),
        itemClickListener = onClick
    )
}

@Composable
fun MovieModel.ShowcaseWidget(onClick: (String) -> Unit) {
    MovieShowcaseWidget(
        movieId = id,
        backgroundImageUrl = ImageApi.getFullUrl(this.posterPath, ImageSize.W780),
        backdropUrl = ImageApi.getFullUrl(this.backdropPath, ImageSize.W780),
        movieName = this.displayTitle(),
        rating = this.display5StarsRating(),
        genres = this.genreList?.map { it.name }?.joinToString(","),
        ratingTotalCountText = this.displayVoteCount(),
        onClickListener = onClick
    )
}