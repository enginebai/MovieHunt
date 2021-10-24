package com.enginebai.moviehunt.ui.home.controller

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.holders.ListSeparator
import com.enginebai.moviehunt.ui.holders.ListSeparator_
import com.enginebai.moviehunt.ui.holders.MovieLandscapeHolder_
import com.enginebai.moviehunt.ui.holders.TitleHolder_
import com.enginebai.moviehunt.ui.home.MovieCategoryListing
import com.enginebai.moviehunt.ui.home.models.HomeLoadInitView_
import com.enginebai.moviehunt.ui.home.models.MovieCarouselModel_
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import kotlin.properties.Delegates

class MovieHomeController(
    private val context: Context,
    private val clickListener: MovieClickListener? = null
) : EpoxyController() {

    var categoryListings: Map<MovieCategory, MovieCategoryListing>? = null
    var upcomingMovieList: List<MovieModel>? by Delegates.observable(null) { _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        categoryListings?.forEach { (category, listing) ->
            if (category != MovieCategory.UPCOMING || (
                        category == MovieCategory.UPCOMING && upcomingMovieList?.isNotEmpty() == true)
            )
                for (i in 1..4) ListSeparator_().id("${ListSeparator::class.java.simpleName} $category padding top")
                    .addTo(this)
            TitleHolder_()
                .id("${category.key} header")
                .title(context.getString(category.strRes))
                .onClickListener {
                    listing.headerClickListener?.onViewAllClicked(category)
                }
                .addTo(this)
            for (i in 1..1) ListSeparator_().id("${ListSeparator::class.java.simpleName} $category padding bottom")
                .addTo(this)
            if (listing.loadingState == NetworkState.LOADING) {
                HomeLoadInitView_()
                    .id("${category.key} loading")
                    .addTo(this)
            } else {
                if (category != MovieCategory.UPCOMING) {
                    MovieCarouselModel_()
                        .id("${category.key} list")
                        .models(emptyList()) // this is required
                        .epoxyController(listing.carouselController)
                        .numViewsToShowOnScreen(listing.itemCountOnScreen)
                        .addTo(this)
                } else {
                    ListSeparator_().id("${ListSeparator::class.java.simpleName} $category padding bottom")
                        .addTo(this)
                    upcomingMovieList?.forEachIndexed { index, movie ->
                        MovieLandscapeHolder_()
                            .id(movie.id)
                            .movieId(movie.id)
                            .imagePoster(movie.getPosterUrl())
                            .textTitle(movie.displayTitle())
                            .rating(movie.display5StarsRating())
                            .ratingTotalCountText(movie.displayVoteCount())
                            .genre(movie.genreList?.map { it.name }?.joinToString())
                            .releaseDateText(movie.releaseDate?.format())
                            .itemClickListener { clickListener?.onMovieClicked(movie.id) }
                            .addTo(this)
                        if (index < upcomingMovieList!!.size - 1)
                            for (i in 1..2)
                                ListSeparator_()
                                    .id("${ListSeparator::class.java.simpleName} $index $i")
                                    .addTo(this)
                    }
                }
            }
        }
    }
}