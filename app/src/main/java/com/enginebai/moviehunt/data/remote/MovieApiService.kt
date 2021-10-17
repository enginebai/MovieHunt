package com.enginebai.moviehunt.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("genre/movie/list")
    fun fetchGenreList(): Single<GenreListing>

    @GET("movie/{list}")
    fun fetchMovieList(
        @Path("list") list: String,
        @Query("page") page: Int? = null
    ): Single<TmdbApiResponse<MovieListResponse>>

    @GET("movie/{movieId}")
    fun fetchMovieDetail(@Path("movieId") movieId: String): Single<MovieDetailResponse>

}