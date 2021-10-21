package com.enginebai.moviehunt.ui.detail

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.display5StarsRating
import com.enginebai.moviehunt.data.local.displayDuration
import com.enginebai.moviehunt.data.local.displayVoteCount
import com.enginebai.moviehunt.data.remote.Video
import com.enginebai.moviehunt.ui.detail.holders.MovieInfoHolder
import com.enginebai.moviehunt.ui.detail.holders.MovieInfoHolder_
import com.enginebai.moviehunt.ui.detail.holders.MovieTrailerHolder
import com.enginebai.moviehunt.ui.detail.holders.MovieTrailerHolder_
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import kotlin.properties.Delegates

interface MovieDetailClickListener {
    fun onTrailerClicked(trailerVideo: String)
}

class MovieDetailController(
    private val clickListener: MovieDetailClickListener
) : EpoxyController() {

    var detail by Delegates.observable<MovieModel?>(null) { _, _, _ ->
        requestModelBuild()
    }
    var videos by Delegates.observable<List<Video>?>(null) { _, _, _ ->
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

        videos?.let {
            val trailerHolders = mutableListOf<MovieTrailerHolder_>()
            it.forEach { video ->
                trailerHolders.add(
                    MovieTrailerHolder_()
                        .id("${MovieTrailerHolder::class.java.simpleName} ${video.id}")
                            .thumbnail(video.youtubeThumbnail)
                        .onTrailerPlayed {
                            clickListener.onTrailerClicked(video.youtubeVideo)
                        }
                            .trailerUrl(video.youtubeVideo)
                )
            }

            carousel {
                id(MovieTrailerHolder::class.java.simpleName)
                        paddingRes(R.dimen.size_8)
                    .models(trailerHolders)
            }
        }
    }
}