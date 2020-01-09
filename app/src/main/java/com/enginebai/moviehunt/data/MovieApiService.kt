package com.enginebai.moviehunt.data

import com.enginebai.moviehunt.data.remote.MovieDetailResponse
import com.enginebai.moviehunt.data.remote.TmdbApiModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {

    @GET("/movie/{movieList}")
    fun fetchMovieList(@Path("movieList") movieList: String): Single<TmdbApiModel>

    @GET("/movie/{movie_id}")
    fun fetchMovieDetail(@Path("movie_id") movieId: String): Single<MovieDetailResponse>
}