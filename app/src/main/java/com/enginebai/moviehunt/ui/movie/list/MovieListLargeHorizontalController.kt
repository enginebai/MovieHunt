package com.enginebai.moviehunt.ui.movie.list

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.MoviePortrailLargeBindingModel_
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.utils.format
import com.enginebai.moviehunt.utils.formatHourMinutes

class MovieListLargeHorizontalController(
    private val clickListener: OnMovieClickListener? = null
) : PagedListEpoxyController<MovieModel>() {

    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MoviePortrailLargeBindingModel_()
                .id(this.id)
                .posterImage(this.posterPath)
                .title(this.title)
                .rating(this.voteAverage)
                .voteCount(this.voteCount?.format() ?: "--")
                .duration(this.runtime?.formatHourMinutes() ?: "--")
                .clickListener(clickListener)
        } ?: run {
            MoviePortrailLargeBindingModel_()
                .id(-currentPosition)
        }
    }
}