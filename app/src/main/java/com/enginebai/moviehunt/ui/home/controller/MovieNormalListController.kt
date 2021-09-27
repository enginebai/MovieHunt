package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.MovieHomeLargeBindingModel_
import com.enginebai.moviehunt.MovieHomeNormalBindingModel_
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.holders.MoviePortraitHolder_
import com.enginebai.moviehunt.ui.list.MovieCategory

class MovieNormalListController(
    movieCategory: MovieCategory,
    clickListener: MovieClickListener? = null
) : MovieCarouselController(movieCategory, clickListener) {
    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MoviePortraitHolder_()
                .id("${movieCategory}${this.id}")
                .movieId(this.id)
                .posterUrl(this.getPosterUrl())
                .movieName(this.displayTitle())
                .rating(this.display5StarsRating())
                .ratingTotalCount(this.voteCount)
                .genre(this.genreList?.firstOrNull()?.name)
                .releaseYear(1234)
                .onClickListener {
                    clickListener?.onMovieClicked(this.id)
                }
        } ?: run {
            MovieHomeLargeBindingModel_()
                .id(-currentPosition)
        }
    }
}