package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyModel
import com.enginebai.moviehunt.MovieHomeLargeBindingModel_
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.list.MovieCategory

class MovieLargeListController(
	movieCategory: MovieCategory,
	clickListener: MovieClickListener? = null
): MovieCarouselController(movieCategory, clickListener) {
	override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
		return item?.run {
			MovieHomeLargeBindingModel_()
				.id("${movieCategory}${this.id}")
				.movieId(this.id)
				.posterImage(this.getPosterUrl())
				.title(this.displayTitle())
				.rating(this.voteAverage)
				.voteCount(this.displayVoteCount())
				.duration(this.displayDuration())
				.clickListener(clickListener)
		} ?: run {
			MovieHomeLargeBindingModel_()
				.id(-currentPosition)
		}
	}
}