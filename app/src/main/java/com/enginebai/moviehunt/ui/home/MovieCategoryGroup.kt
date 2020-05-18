package com.enginebai.moviehunt.ui.home

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.enginebai.moviehunt.R

class MovieCategoryGroup(
	movieCategoryListing: MovieCategoryListing
) : EpoxyModelGroup(
	R.layout.group_movie_category,
	buildModels(movieCategoryListing)
) {
	companion object {
		fun buildModels(
			categoryListing: MovieCategoryListing
		): List<EpoxyModel<*>> {
			return listOf(
				CategoryHeaderHolder_()
					.id("${categoryListing.category.key}-header")
					.category(categoryListing.category)
					.clickListener(categoryListing.headerClickListener),
				MovieCarouselModel_()
					.id("${categoryListing.category.key}-list")
					.models(emptyList()) // this is required
					.epoxyController(categoryListing.carouselController)
					.paddingRes(R.dimen.size_8)
					.numViewsToShowOnScreen(categoryListing.itemCountOnScreen)
			)
		}
	}
}