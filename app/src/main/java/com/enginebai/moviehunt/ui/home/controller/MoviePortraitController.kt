package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.MovieHomeLargeBindingModel_
import com.enginebai.moviehunt.MovieHomeNormalBindingModel_
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.holders.MoviePortraitHolder_
import com.enginebai.moviehunt.ui.list.MovieCategory
import java.util.*

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