package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.home.*
import com.enginebai.moviehunt.ui.list.MovieCategory

abstract class MovieCarouselController(
	protected val movieCategory: MovieCategory,
	protected val clickListener: MovieClickListener? = null
) : PagedListEpoxyController<MovieModel>() {

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

	private val loadingInitView =
		HomeLoadInitView_().apply { id(HomeLoadInitView::class.java.simpleName) }
	private val loadingMoreView =
		HomeLoadMoreView_().apply { id(HomeLoadMoreView::class.java.simpleName) }

	override fun addModels(models: List<EpoxyModel<*>>) {
		loadingInitView.addIf(loadingInit && models.isEmpty(), this)
		super.addModels(models)
		loadingMoreView.addIf(loadingMore && models.isNotEmpty(), this)
	}
}