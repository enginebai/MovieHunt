package com.enginebai.moviehunt.ui.movie.home

import com.airbnb.epoxy.EpoxyController

class MovieHomeController : EpoxyController() {

    var categoryList: List<MovieCategoryListing> = listOf()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        categoryList.forEach {
            MovieCategoryGroup(it).addTo(this)
        }
    }
}