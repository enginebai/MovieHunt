package com.enginebai.moviehunt.ui.movie.home

import com.enginebai.base.utils.Listing
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import org.koin.core.inject

class MovieHomeViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    private val refreshTaskMap = mutableMapOf<MovieCategory, () -> Unit>()

    fun fetchList(category: MovieCategory): Listing<MovieModel> {
        val listing = movieRepo.fetchMovieList(category)
        refreshTaskMap[category] = listing.refresh
        return listing
    }

    fun refresh() {
        refreshTaskMap.values.forEach {
            it.invoke()
        }
    }
}