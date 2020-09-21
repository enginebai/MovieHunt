package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.MovieHomeLargeBindingModel_
import com.enginebai.moviehunt.MovieHomeNormalBindingModel_
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.displayTitle
import com.enginebai.moviehunt.data.local.displayVotePercentage
import com.enginebai.moviehunt.data.local.getPosterUrl
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.list.MovieCategory

class MovieNormalListController(
    movieCategory: MovieCategory,
    clickListener: MovieClickListener? = null
) : MovieCarouselController(movieCategory, clickListener) {
    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MovieHomeNormalBindingModel_()
                .id("${movieCategory}${this.id}")
                .movieId(this.id)
                .posterImage(this.getPosterUrl())
                .title(this.displayTitle())
                .rating(this.displayVotePercentage())
                .clickListener(clickListener)
        } ?: run {
            MovieHomeLargeBindingModel_()
                .id(-currentPosition)
        }
    }
}