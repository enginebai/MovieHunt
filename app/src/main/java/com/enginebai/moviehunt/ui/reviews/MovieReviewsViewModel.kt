package com.enginebai.moviehunt.ui.reviews

import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import org.koin.core.component.inject

class MovieReviewsViewModel : BaseViewModel() {
    private val movieRepo by inject<MovieRepo>()

    fun fetchReviews(movieId: String) = movieRepo.fetchMovieReviewPagingData(movieId)
}