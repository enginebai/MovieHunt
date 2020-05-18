package com.enginebai.moviehunt.ui.home

import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.list.MovieCategory

data class MovieCategoryListing(
	val category: MovieCategory,
	val headerClickListener: CategoryHeaderHolder.OnHeaderClickListener? = null,
	val carouselController: PagedListEpoxyController<MovieModel>,
	val itemCountOnScreen: Float = 0.0f
)