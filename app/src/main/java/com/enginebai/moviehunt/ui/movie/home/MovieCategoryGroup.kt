package com.enginebai.moviehunt.ui.movie.home

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.enginebai.moviehunt.R
import timber.log.Timber

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
            Timber.d("$categoryListing")
            return listOf(
                CategoryHeaderHolder_()
                    .id("${categoryListing.category}-header")
                    .clickListener(categoryListing.headerClickListener),
                MovieCarouselModel_()
                    .id("${categoryListing.category}-list")
                    .epoxyController(categoryListing.carouselController)
            )
        }
    }
}