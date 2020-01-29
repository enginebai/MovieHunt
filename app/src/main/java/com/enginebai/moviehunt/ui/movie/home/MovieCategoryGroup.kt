package com.enginebai.moviehunt.ui.movie.home

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.enginebai.moviehunt.R

class MovieCategoryGroup(
    category: String,
    clickListener: CategoryHeaderHolder.OnHeaderClickListener? = null,
    carouselController: EpoxyController
) : EpoxyModelGroup(
    R.layout.group_movie_category,
    buildModels(category, clickListener, carouselController)
) {
    companion object {
        fun buildModels(
            category: String,
            clickListener: CategoryHeaderHolder.OnHeaderClickListener? = null,
            carouselController: EpoxyController
        ): List<EpoxyModel<*>> {
            return listOf(
                CategoryHeaderHolder_().id("$category-header").clickListener(clickListener),
                MovieCarouselModel_().id("$category-list").epoxyController(carouselController)
            )
        }
    }
}