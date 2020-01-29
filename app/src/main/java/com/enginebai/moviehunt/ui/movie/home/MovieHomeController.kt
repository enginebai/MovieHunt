package com.enginebai.moviehunt.ui.movie.home

import com.airbnb.epoxy.EpoxyController
import timber.log.Timber

class MovieHomeController : EpoxyController() {

    var categoryList: List<MovieCategoryListing> = listOf()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        categoryList.forEach {
            Timber.d("Add $it")
            MovieCategoryGroup(it).addTo(this)
        }
    }
}