package com.enginebai.moviehunt.ui.movie.home

import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.data.local.MovieModel

data class MovieCategoryListing(
    val category: MovieCategory,
    val headerClickListener: CategoryHeaderHolder.OnHeaderClickListener? = null,
    val carouselController: PagedListEpoxyController<MovieModel>,
    val itemCountOnScreen: Float = 0.0f
)