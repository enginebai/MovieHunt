package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.BuildConfig
import com.enginebai.moviehunt.MovieHomeLargeBindingModel_
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.ui.widgets.MovieShowcaseHolder_

class MovieShowcaseController(
    movieCategory: MovieCategory,
    clickListener: MovieClickListener? = null
) : MovieCarouselController(movieCategory, clickListener) {
    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MovieShowcaseHolder_()
                .id("${movieCategory}${this.id}")
                .movieId(this.id)
                .backgroundImageUrl(this.getPosterUrlWithLargeSize())
                    // TODO: Image API
                .backdropUrl("${BuildConfig.IMAGE_API_KEY}w780/${this.backdropPath}")
                .movieName(this.displayTitle())
                .rating(this.display5StarsRating())
                .genres(this.genreList?.map { it.name }?.joinToString(","))
                .ratingTotalCountText(this.displayVoteCount())
                .onClickListener {
                    clickListener?.onMovieClicked(this.id)
                }
        } ?: run {
            MovieShowcaseHolder_()
                .id(-currentPosition)
        }
    }
}