package com.enginebai.moviehunt.ui.movie.list

import com.enginebai.base.utils.Listing
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import com.enginebai.moviehunt.ui.movie.home.MovieCategory
import org.koin.core.inject

class MovieListViewModelV2 : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    fun fetchList(category: MovieCategory): Listing<MovieModel> {
        return movieRepo.fetchMovieList(category)
    }

    fun getList(category: MovieCategory): Listing<MovieModel> {
        return movieRepo.getMovieList(category)
    }
}