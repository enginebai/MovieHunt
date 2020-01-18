package com.enginebai.moviehunt.ui.movie.list

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener
import com.enginebai.moviehunt.ui.movie.list.category.MovieInCategoryEpoxyModel_

class MovieListController(
    private val clickListener: OnMovieClickListener
) : PagedListEpoxyController<MovieModel>() {
    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MovieInCategoryEpoxyModel_()
                .id(this.id)
                .movieId(this.id)
                .imagePoster(this.getPosterUrl())
                .textTitle(this.displayTitle())
                .rating(this.voteAverage?.div(2) ?: 0.0f)
                .voteCount(this.displayVoteCount())
                .duration(this.displayDuration())
                .releaseDate(this.displayReleaseDate())
                .itemClickListener { clickListener.onMovieClicked(this.id) }
        } ?: run {
            MovieInCategoryEpoxyModel_()
                .id(-currentPosition)
        }
    }
}