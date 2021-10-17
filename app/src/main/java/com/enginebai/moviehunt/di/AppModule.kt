package com.enginebai.moviehunt.di

import com.enginebai.moviehunt.MainViewModel
import com.enginebai.moviehunt.ui.home.MovieHomeViewModel
import com.enginebai.moviehunt.ui.list.MovieListViewModelV1
import com.enginebai.moviehunt.ui.list.MovieListViewModelV2
import com.enginebai.moviehunt.ui.detail.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
}

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { MovieHomeViewModel() }
    viewModel { MovieListViewModelV1() }
    viewModel { MovieListViewModelV2() }
    viewModel { MovieDetailViewModel() }
}