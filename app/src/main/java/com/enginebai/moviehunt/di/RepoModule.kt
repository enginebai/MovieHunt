package com.enginebai.moviehunt.di

import com.enginebai.moviehunt.data.repo.MovieRepo
import com.enginebai.moviehunt.data.repo.MovieRepoImpl
import org.koin.dsl.module

val repoModule = module {
    single<MovieRepo> { MovieRepoImpl() }
}