package com.enginebai.moviehunt.data.remote

import androidx.room.ColumnInfo
import com.enginebai.moviehunt.data.local.MovieModel
import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("poster_path")
    // 這邊使用 @ColumnInfo 是為了讓這個欄位可以在 DAO 直接使用 target entity 來更新 @Entity
    // 要讓欄位名稱可以對的起來而加上去的，不是為了存資料庫欄位
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int?,
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String?
)

fun List<MovieListResponse>.mapToMovieModels(): List<MovieModel> =
    this.map {
        MovieModel(
            id = it.id,
            posterPath = it.posterPath,
            title = it.title,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            releaseDate = it.releaseDate
        )
    }

data class MovieDetailResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("genres")
    val genreList: List<Genre>?,
    @SerializedName("runtime")
    val runtime: Int?
)

data class Genre(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?
)