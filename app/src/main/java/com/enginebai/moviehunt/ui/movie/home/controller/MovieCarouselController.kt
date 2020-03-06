package com.enginebai.moviehunt.ui.movie.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener
import com.enginebai.moviehunt.ui.movie.home.HomeLoadInitView
import com.enginebai.moviehunt.ui.movie.home.HomeLoadInitView_
import com.enginebai.moviehunt.ui.movie.home.HomeLoadMMoreView_
import com.enginebai.moviehunt.ui.movie.home.MovieCategory

abstract class MovieCarouselController(
    protected val movieCategory: MovieCategory,
    protected val clickListener: OnMovieClickListener? = null
) : PagedListEpoxyController<MovieModel>() {
    private val loadingInitView =
        HomeLoadInitView_()
            .apply { id(HomeLoadInitView::class.java.simpleName) }
    private val loadingMoreView =
        HomeLoadMMoreView_()
            .apply { id(HomeLoadInitView::class.java.simpleName) }
    var loadingInit = false
        set(value) {
            field = value
            requestModelBuild()
        }
    var loadingMore = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun addModels(models: List<EpoxyModel<*>>) {
        loadingInitView.addIf(loadingInit && models.isEmpty(), this)
        super.addModels(models)
        loadingMoreView.addIf(loadingMore && models.isNotEmpty(), this)
    }
}