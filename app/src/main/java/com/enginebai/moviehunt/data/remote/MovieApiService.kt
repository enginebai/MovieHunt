package com.enginebai.moviehunt.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    companion object {
        private const val PARAM_MOVIE_ID = "movieId"

        private const val PATH_MOVIE_DETAIL = "movie/{$PARAM_MOVIE_ID}"
    }

    @GET("genre/movie/list")
    fun fetchGenreList(): Single<GenreListing>

    @GET("movie/{list}")
    suspend fun fetchMovieList(
        @Path("list") list: String,
        @Query("page") page: Int? = null
    ): TmdbApiResponse<MovieListResponse>

    @GET(PATH_MOVIE_DETAIL)
    fun fetchMovieDetail(@Path(PARAM_MOVIE_ID) movieId: String): Single<MovieDetailResponse>

    @GET("$PATH_MOVIE_DETAIL + /videos")
    fun fetchMovieVideos(@Path(PARAM_MOVIE_ID) movieId: String): Single<TmdbApiResponse<Video>>

    @GET("$PATH_MOVIE_DETAIL + /reviews")
    suspend fun fetchMovieReviews(
        @Path(PARAM_MOVIE_ID) movieId: String,
        @Query("page") page: Int? = null
    ): TmdbApiResponse<Review>

    @GET("$PATH_MOVIE_DETAIL + /credits")
    fun fetchMovieCasts(@Path(PARAM_MOVIE_ID) movieId: String): Single<CastListing>

    @GET("$PATH_MOVIE_DETAIL + /similar")
    fun fetchSimilarMovies(@Path(PARAM_MOVIE_ID) movieId: String): Single<TmdbApiResponse<MovieListResponse>>

    @GET("$PATH_MOVIE_DETAIL + /recommendations")
    fun fetchRecommendationMovies(@Path(PARAM_MOVIE_ID) movieId: String): Single<TmdbApiResponse<MovieListResponse>>
}