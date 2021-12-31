package com.enginebai.moviehunt.ui.home

import androidx.paging.LoadState
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.list.MovieCategory

interface OnHeaderClickListener {
    fun onViewAllClicked(category: MovieCategory)
}

data class MovieCategoryListing(
    val headerClickListener: OnHeaderClickListener? = null,
    var loadingState: LoadState? = null,
    val carouselController: PagingDataEpoxyController<MovieModel>,
    val itemCountOnScreen: Float = 0.0f
)
