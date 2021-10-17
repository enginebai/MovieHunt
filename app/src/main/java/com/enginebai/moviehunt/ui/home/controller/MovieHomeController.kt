package com.enginebai.moviehunt.ui.home.controller

import com.airbnb.epoxy.EpoxyController
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.ui.holders.TitleHolder_
import com.enginebai.moviehunt.ui.home.MovieCategoryListing
import com.enginebai.moviehunt.ui.home.models.HomeLoadInitView_
import com.enginebai.moviehunt.ui.home.models.MovieCarouselModel_
import com.enginebai.moviehunt.ui.list.MovieCategory

class MovieHomeController : EpoxyController() {

    var categoryListings: Map<MovieCategory, MovieCategoryListing>? = null

    override fun buildModels() {
        categoryListings?.forEach { (category, listing) ->
            TitleHolder_()
                .id("${category.key}-header")
                .title(category.name)
                .onClickListener {
                    listing.headerClickListener?.onViewAllClicked(category)
                }
                .addTo(this)
            if (listing.loadingState == NetworkState.LOADING) {
                HomeLoadInitView_()
                    .id("${category.key}-loading")
                    .addTo(this)
            } else {
                MovieCarouselModel_()
                    .id("${category.key}-list")
                    .models(emptyList()) // this is required
                    .epoxyController(listing.carouselController)
                    .paddingRes(R.dimen.size_8)
                    .numViewsToShowOnScreen(listing.itemCountOnScreen)
                    .addTo(this)
            }
        }
    }
}