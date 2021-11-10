package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.BuildConfig
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.display5StarsRating
import com.enginebai.moviehunt.data.local.displayTitle
import com.enginebai.moviehunt.data.local.displayVoteCount
import com.enginebai.moviehunt.data.remote.ImageApi
import com.enginebai.moviehunt.data.remote.ImageSize
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
                .backgroundImageUrl(ImageApi.getFullUrl(this.posterPath, ImageSize.W780))
                .backdropUrl(ImageApi.getFullUrl(this.backdropPath, ImageSize.W780))
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