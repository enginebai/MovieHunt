package com.enginebai.moviehunt.data.remote

import com.google.gson.annotations.SerializedName

data class TmdbApiModel(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("results")
    val results: List<MovieListResponse>? = null
)