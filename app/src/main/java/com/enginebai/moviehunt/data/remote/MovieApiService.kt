package com.enginebai.moviehunt.data.remote

import com.google.gson.Gson
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
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

class MockMovieApiService : MovieApiService, KoinComponent {
    private val gson: Gson by inject()

    override fun fetchMovieList(movieList: String, page: Int?): Single<TmdbApiModel> {
        val r = """
            {
                "page": $page,
                "total_results": 10000,
                "total_pages": 5,
                "results": [
                    {
                        "popularity": 29.278,
                        "vote_count": 6173,
                        "video": false,
                        "poster_path": "/fMMrl8fD9gRCFJvsx0SuFwkEOop.jpg",
                        "id": ${System.currentTimeMillis()},
                        "adult": false,
                        "backdrop_path": "/qrtRKRzoNkf5vemO9tJ2Y4DrHxQ.jpg",
                        "original_language": "en",
                        "original_title": "Fantastic Beasts: The Crimes of Grindelwald",
                        "genre_ids": [
                            12,
                            14,
                            10751
                        ],
                        "title": "$page Fantastic Beasts: The Crimes of Grindelwald",
                        "vote_average": 6.8,
                        "overview": "Gellert Grindelwald .",
                        "release_date": "2018-11-14"
                    }
                ]
            }
        """.trimIndent()
        val model = gson.fromJson(r, TmdbApiModel::class.java)
        return Single.just(model)
    }

    override fun fetchMovieDetail(movieId: String): Single<MovieDetailResponse> {
        val r = """
            {
                "adult": false,
                "backdrop_path": "/qonBhlm0UjuKX2sH7e73pnG0454.jpg",
                "belongs_to_collection": null,
                "budget": 85000000,
                "genres": [
                    {
                        "id": 28,
                        "name": "Action"
                    },
                    {
                        "id": 878,
                        "name": "Science Fiction"
                    },
                    {
                        "id": 35,
                        "name": "Comedy"
                    },
                    {
                        "id": 10751,
                        "name": "Family"
                    }
                ],
                "homepage": "",
                "id": 454626,
                "imdb_id": "tt3794354",
                "original_language": "en",
                "original_title": "Sonic the Hedgehog",
                "overview": "Based on the global .",
                "popularity": 294.49,
                "poster_path": "/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg",
                "release_date": "2020-02-12",
                "revenue": 265493652,
                "runtime": 99,
                "spoken_languages": [
                    {
                        "iso_639_1": "en",
                        "name": "English"
                    }
                ],
                "status": "Released",
                "tagline": "A Whole New Speed of Hero",
                "title": "Sonic the Hedgehog",
                "video": false,
                "vote_average": 7.1,
                "vote_count": 504
            }
        """.trimIndent()
        val model = gson.fromJson(r, MovieDetailResponse::class.java)
        return Single.just(model)
    }
}