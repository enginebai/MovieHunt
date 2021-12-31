package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.home.models.HomeLoadMoreView
import com.enginebai.moviehunt.ui.home.models.HomeLoadMoreView_
import com.enginebai.moviehunt.ui.list.MovieCategory

abstract class MovieCarouselController(
    protected val movieCategory: MovieCategory,
    protected val clickListener: MovieClickListener? = null
) : PagingDataEpoxyController<MovieModel>() {

    var loadingMore = false
        set(value) {
            field = value
            requestModelBuild()
        }

    private val loadingMoreView =
        HomeLoadMoreView_().apply { id(HomeLoadMoreView::class.java.simpleName) }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        loadingMoreView.addIf(loadingMore && models.isNotEmpty(), this)
    }
}