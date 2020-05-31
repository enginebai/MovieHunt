package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyController
import com.enginebai.moviehunt.ui.home.MovieCategoryGroup
import com.enginebai.moviehunt.ui.home.MovieCategoryListing

class MovieHomeController : EpoxyController() {

	var categoryListings: List<MovieCategoryListing> = listOf()
		set(value) {
			field = value
			requestModelBuild()
		}

	override fun buildModels() {
		categoryListings.forEach {
			MovieCategoryGroup(it).addTo(this)
		}
	}
}