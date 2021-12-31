package com.enginebai.moviehunt.data.remote

import com.google.gson.annotations.SerializedName

data class TmdbApiResponse<T>(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0,
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("results")
    val results: List<T>? = null,
)