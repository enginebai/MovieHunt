package com.enginebai.moviehunt.data.remote

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.repo.ConfigRepo
import com.google.gson.annotations.SerializedName
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

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
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: Calendar?,
    @SerializedName("genres")
    @ColumnInfo(name = "genres")
    var genreList: List<Genre>?,
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = null,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @Ignore
    @SerializedName("genre_ids")
    val genreIds: List<Int>?
)

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
    val releaseDate: Calendar?,
    @SerializedName("genres")
    val genreList: List<Genre>?,
    @SerializedName("runtime")
    val runtime: Int?
)

data class GenreListing(
    @SerializedName("genres")
    val genreList: List<Genre>?
)

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?
)

object MovieModelMapper : KoinComponent {
    private val configRepo by inject<ConfigRepo>()

    fun MovieListResponse.toMovieModel(): MovieModel {
        fillGenreList()
        return MovieModel(
                id = this.id,
                posterPath = this.posterPath,
                title = this.title,
                voteAverage = this.voteAverage,
                voteCount = this.voteCount,
                releaseDate = this.releaseDate,
                backdropPath = this.backdropPath,
                genreList = this.genreList
        )
    }

    fun MovieListResponse.fillGenreList() {
        if (configRepo.genreList.value != null) {
            val genreList = mutableListOf<Genre>()
            this.genreIds?.forEach { id ->
                configRepo.genreList.value!!.find { it.id == id }?.apply {
                    genreList.add(this)
                }
            }
            this.genreList = genreList
        }
    }
}