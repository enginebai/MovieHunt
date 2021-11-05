package com.enginebai.moviehunt.ui.detail

import android.content.Context
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.data.remote.CastListing
import com.enginebai.moviehunt.data.remote.Review
import com.enginebai.moviehunt.data.remote.Video
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.holders.*
import com.enginebai.moviehunt.ui.widgets.*
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import kotlin.properties.Delegates

class MovieDetailController(
    private val context: Context,
    private val trailerClickListener: (String) -> Unit,
    private val reviewSeeAllClickListener: (String) -> Unit,
    private val movieClickListener: MovieClickListener
) : EpoxyController() {

    private val titlePaddingTopCount = 5
    private val titlePaddingBottomCount = 1

    var detail by Delegates.observable<MovieModel?>(null) { _, _, _ ->
        requestModelBuild()
    }
    var videos by Delegates.observable<List<Video>?>(null) { _, _, _ ->
        requestModelBuild()
    }
    var review by Delegates.observable<Review?>(null) { _, _, _ ->
        requestModelBuild()
    }
    var casts by Delegates.observable<List<CastListing.Cast>?>(null) { _, _, _ ->
        requestModelBuild()
    }
    var similarMovies by Delegates.observable<List<MovieModel>?>(null) { _, _, _ ->
        requestModelBuild()
    }
    var recommendationMovies by Delegates.observable<List<MovieModel>?>(null) { _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        val padding: Carousel.Padding = Carousel.Padding.resource(
            R.dimen.page_horizontal_padding,
            R.dimen.size_12,
            R.dimen.page_horizontal_padding,
            R.dimen.size_12,
            R.dimen.size_8
        )
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

        if (!videos.isNullOrEmpty()) {
            buildTitle(context.getString(R.string.title_trailers))
            val trailerHolders = mutableListOf<MovieTrailerHolder_>()
            videos!!.forEach { video ->
                trailerHolders.add(
                    MovieTrailerHolder_()
                        .id("${MovieTrailerHolder::class.java.simpleName} ${video.id}")
                            .thumbnail(video.youtubeThumbnail)
                        .onTrailerPlayed {
                            trailerClickListener(video.youtubeVideo)
                        }
                        .trailerUrl(video.youtubeVideo)
                )
            }

            customCarousel {
                id(MovieTrailerHolder::class.java.simpleName)
                        paddingRes(R.dimen.size_8)
                    models(trailerHolders)
                numViewsToShowOnScreen(2.5f)
                padding(padding)
            }
        }

        review?.let {
            buildTitle(context.getString(R.string.title_reviews)) {
                detail?.id?.run {
                    reviewSeeAllClickListener.invoke(this)
                }
            }
            it.toHolder().addTo(this)
        }

        if (!casts.isNullOrEmpty()) {
            buildTitle(context.getString(R.string.title_casts))
            val castHolders = mutableListOf<MovieCastHolder_>()
            casts!!.forEach {
                castHolders.add(MovieCastHolder_()
                    .id("${MovieCastHolder::class.java.simpleName} ${it.id}")
                    .avatar(it.getAvatarFullPath())
                    .actorName(it.actorName)
                    .character(it.character)
                )
            }

            customCarousel {
                id(MovieCastHolder::class.java.simpleName)
                    .models(castHolders)
                    .padding(padding)
            }
        }

        buildMovieCarousel(context.getString(R.string.title_recommendation_movies), recommendationMovies)
        buildMovieCarousel(context.getString(R.string.title_similar_movies), similarMovies)
    }

    private fun buildTitle(title: String, clickListener: (() -> Unit)? = null) {
        for (i in 1..titlePaddingTopCount)
            ListSeparator_()
                .id("${ListSeparator::class.java.simpleName} $title padding top")
                .addTo(this)
        TitleHolder_()
            .id("${TitleHolder::class.java.simpleName} $title")
            .title(title)
            .onClickListener(clickListener)
            .addTo(this)
        for (i in 1..titlePaddingBottomCount)
            ListSeparator_()
                .id("${ListSeparator::class.java.simpleName} $title padding bottom")
                .addTo(this)
    }

    private fun buildMovieCarousel(title: String, movieList: List<MovieModel>?) {
        if (!movieList.isNullOrEmpty()) {
            buildTitle(title)

            val movieHolders = mutableListOf<MoviePortraitHolder_>()
            movieList.forEach {
                movieHolders.add(
                    it.toPortraitHolder()
                        .id("$title ${it.id}")
                        .onClickListener { id ->
                            movieClickListener.onMovieClicked(id)
                        }
                )
            }

            carousel {
                id(title)
                    .models(movieHolders)
                    // TODO: define constant along with home settings
                    .numViewsToShowOnScreen(3.1f)
            }
        }
    }
}