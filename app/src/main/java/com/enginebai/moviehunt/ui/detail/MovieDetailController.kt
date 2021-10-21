package com.enginebai.moviehunt.ui.detail

import com.airbnb.epoxy.EpoxyController
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.display5StarsRating
import com.enginebai.moviehunt.data.local.displayDuration
import com.enginebai.moviehunt.data.local.displayVoteCount
import com.enginebai.moviehunt.ui.detail.holders.MovieInfoHolder
import com.enginebai.moviehunt.ui.detail.holders.MovieInfoHolder_
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import kotlin.properties.Delegates

class MovieDetailController : EpoxyController() {

    var detail by Delegates.observable<MovieModel?>(null) { _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        detail?.let { detail ->
            MovieInfoHolder_()
                    .id(MovieInfoHolder::class.java.simpleName)
                    .name(detail.title)
                    .rating(detail.display5StarsRating())
                    .ratingTotalCountText(detail.displayVoteCount())
                    .genres(detail.genreList?.map { it.name }?.joinToString(","))
                    .releaseDateText(detail.releaseDate?.format())
                    .runtimeText(detail.displayDuration())
                    .overview(detail.overview)
                    .addTo(this)
        }
    }
}