package com.enginebai.moviehunt.di

import com.enginebai.moviehunt.MainViewModel
import com.enginebai.moviehunt.ui.detail.MovieDetailViewModel
import com.enginebai.moviehunt.ui.home.MovieHomeViewModel
import com.enginebai.moviehunt.ui.list.MovieListViewModel
import com.enginebai.moviehunt.ui.detail.MovieDetailViewModelV1
import com.enginebai.moviehunt.ui.reviews.MovieReviewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
}

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { MovieHomeViewModel() }
    viewModel { MovieListViewModel() }
    viewModel { MovieDetailViewModelV1() }
    viewModel { MovieDetailViewModel() }
    viewModel { MovieReviewsViewModel() }
}