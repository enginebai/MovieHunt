package com.enginebai.moviehunt.ui.movie.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.MoviePortrailLargeBindingModel_
import com.enginebai.moviehunt.MoviePortrailNormalBindingModel_
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.displayTitle
import com.enginebai.moviehunt.data.local.displayVotePercentage
import com.enginebai.moviehunt.data.local.getPosterUrl
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener
import com.enginebai.moviehunt.ui.movie.home.MovieCategory

class MovieNormalListController(
    movieCategory: MovieCategory,
    clickListener: OnMovieClickListener? = null
) : MovieCarouselController(movieCategory, clickListener) {
    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MoviePortrailNormalBindingModel_()
                .id("${movieCategory}${this.id}")
                .movieId(this.id)
                .posterImage(this.getPosterUrl())
                .title(this.displayTitle())
                .rating(this.displayVotePercentage())
                .clickListener(clickListener)
        } ?: run {
            MoviePortrailLargeBindingModel_()
                .id(-currentPosition)
        }
    }
}