package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.widgets.MoviePortraitHolder_
import com.enginebai.moviehunt.ui.list.MovieCategory

class MoviePortraitController(
    movieCategory: MovieCategory,
    clickListener: MovieClickListener? = null
) : MovieCarouselController(movieCategory, clickListener) {
    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            this.toPortraitHolder()
                .id("${movieCategory}${this.id}")
                .onClickListener {
                    clickListener?.onMovieClicked(this.id)
                }
        } ?: run {
            MoviePortraitHolder_()
                .id(-currentPosition)
        }
    }
}