package com.enginebai.moviehunt.di

import com.enginebai.moviehunt.data.remote.MockMovieApiService
import com.enginebai.moviehunt.data.remote.MovieApiService
import org.koin.dsl.module

val apiModule = module {
//    single { get<Retrofit>().create(MovieApiService::class.java) }
    single<MovieApiService> { MockMovieApiService() }
}