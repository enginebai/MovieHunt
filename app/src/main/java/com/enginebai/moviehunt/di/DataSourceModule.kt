package com.enginebai.moviehunt.di

import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.repo.MovieRepo
import com.enginebai.moviehunt.data.repo.MovieRepoImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(MovieApiService::class.java) }
}

val repoModule = module {
    single<MovieRepo> { MovieRepoImpl() }
}