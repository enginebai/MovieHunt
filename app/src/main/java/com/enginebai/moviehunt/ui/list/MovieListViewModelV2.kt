package com.enginebai.moviehunt.ui.list

import com.enginebai.base.utils.Listing
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import org.koin.core.component.inject

class MovieListViewModelV2 : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    fun fetchPagedListing(category: MovieCategory): Listing<MovieModel> {
        return movieRepo.fetchMoviePagedListing(category)
    }

    fun getPagedListing(category: MovieCategory): Listing<MovieModel> {
        return movieRepo.getMoviePagedListing(category)
    }
}