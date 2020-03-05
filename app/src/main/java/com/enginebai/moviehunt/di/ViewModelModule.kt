package com.enginebai.moviehunt.di

import com.enginebai.moviehunt.ui.movie.detail.MovieDetailViewModel
import com.enginebai.moviehunt.ui.movie.home.MovieHomeViewModel
import com.enginebai.moviehunt.ui.movie.list.MovieListViewModelV2
import com.enginebai.moviehunt.ui.movie.list.MovieListViewModelV1
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModelV1() }
    viewModel { MovieListViewModelV2() }
    viewModel { MovieHomeViewModel() }
    viewModel { MovieDetailViewModel() }
}