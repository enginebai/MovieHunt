package com.enginebai.moviehunt.ui.home

import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.home.models.CategoryHeaderHolder

data class MovieCategoryListing(
	val headerClickListener: CategoryHeaderHolder.OnHeaderClickListener? = null,
	var loadingState: NetworkState? = null,
	val carouselController: PagedListEpoxyController<MovieModel>,
	val itemCountOnScreen: Float = 0.0f
)
