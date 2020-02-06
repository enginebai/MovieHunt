package com.enginebai.moviehunt.di

import com.enginebai.moviehunt.ui.movie.detail.MovieDetailViewModel
import com.enginebai.moviehunt.ui.movie.list.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModel() }
    viewModel { MovieDetailViewModel() }
}