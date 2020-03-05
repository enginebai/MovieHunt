package com.enginebai.moviehunt.ui.movie.home

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.MoviePortrailLargeBindingModel_
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener

class MovieLargeListController(
    private val movieCategory: MovieCategory,
    private val clickListener: OnMovieClickListener? = null
) : PagedListEpoxyController<MovieModel>() {

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