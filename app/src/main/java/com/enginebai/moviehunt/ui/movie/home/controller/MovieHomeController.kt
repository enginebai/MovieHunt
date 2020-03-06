package com.enginebai.moviehunt.ui.movie.home.controller

import com.airbnb.epoxy.EpoxyController
import com.enginebai.moviehunt.ui.movie.home.MovieCategoryGroup
import com.enginebai.moviehunt.ui.movie.home.MovieCategoryListing

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