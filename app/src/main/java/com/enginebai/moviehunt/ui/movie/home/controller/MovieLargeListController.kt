package com.enginebai.moviehunt.ui.movie.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.MoviePortrailLargeBindingModel_
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener
import com.enginebai.moviehunt.ui.movie.home.MovieCategory

class MovieLargeListController(
    movieCategory: MovieCategory,
    clickListener: OnMovieClickListener? = null
) : MovieCarouselController(movieCategory, clickListener) {

    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MoviePortrailLargeBindingModel_()
                .id("${movieCategory}${this.id}")
                .movieId(this.id)
                .posterImage(this.getPosterUrl())
                .title(this.displayTitle())
                .rating(this.voteAverage)
                .voteCount(this.displayVoteCount())
                .duration(this.displayDuration())
                .clickListener(clickListener)
        } ?: run {
            MoviePortrailLargeBindingModel_()
                .id(-currentPosition)
        }
    }
}