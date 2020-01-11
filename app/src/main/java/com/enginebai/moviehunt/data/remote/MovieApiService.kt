package com.enginebai.moviehunt.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/{movieList}")
    fun fetchMovieList(
        @Path("movieList") movieList: String,
        @Query("page") page: Int? = null
    ): Single<TmdbApiModel>

    @GET("movie/{movie_id}")
    fun fetchMovieDetail(@Path("movie_id") movieId: String): Single<MovieDetailResponse>
}