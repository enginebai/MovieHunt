package com.enginebai.moviehunt.ui.detail

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.data.remote.CastListing
import com.enginebai.moviehunt.data.remote.Review
import com.enginebai.moviehunt.data.remote.Video
import com.enginebai.moviehunt.data.remote.getAvatar
import com.enginebai.moviehunt.ui.detail.holders.*
import com.enginebai.moviehunt.ui.holders.MoviePortraitHolder_
import com.enginebai.moviehunt.ui.holders.TitleHolder
import com.enginebai.moviehunt.ui.holders.TitleHolder_
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import kotlin.properties.Delegates

interface MovieDetailClickListener {
    fun onTrailerClicked(trailerVideo: String)
}

class MovieDetailController(
    private val context: Context,
    private val clickListener: MovieDetailClickListener
) : EpoxyController() {

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
            TitleHolder_()
                .id("${TitleHolder::class.java.simpleName} ${MovieTrailerHolder::class.java.simpleName}")
                .title(context.getString(R.string.title_trailers))
                .addTo(this)
            val trailerHolders = mutableListOf<MovieTrailerHolder_>()
            videos!!.forEach { video ->
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
                    models(trailerHolders)
                numViewsToShowOnScreen(2.5f)
            }
        }

        review?.let {
            TitleHolder_()
                .id("${TitleHolder::class.java.simpleName} ${MovieReviewHolder::class.java.simpleName}")
                .title(context.getString(R.string.title_reviews))
                .addTo(this)
            MovieReviewHolder_()
                .id(MovieReviewHolder::class.java.simpleName)
                .avatar(it.author?.getAvatar())
                .name(it.author?.username)
                // TODO: rating
                .rating(9.5f)
                .comment(it.content)
                .createdAtDateText(it.createdAt?.format())
                .addTo(this)
        }

        if (!casts.isNullOrEmpty()) {
            TitleHolder_()
                .id("${TitleHolder::class.java.simpleName} ${MovieReviewHolder::class.java.simpleName}")
                .title(context.getString(R.string.title_casts))
                .addTo(this)

            val castHolders = mutableListOf<MovieCastHolder_>()
            casts!!.forEach {
                castHolders.add(MovieCastHolder_()
                    .id("${MovieCastHolder::class.java.simpleName} ${it.id}")
                    .avatar(it.getAvatar())
                    .actorName(it.actorName)
                    .character(it.character)
                )
            }

            carousel {
                id(MovieCastHolder::class.java.simpleName)
                    .models(castHolders)
            }
        }

        buildMovieCarousel(context.getString(R.string.title_similar_movies), similarMovies)
        buildMovieCarousel(context.getString(R.string.title_recommendation_movies), recommendationMovies)
    }

    private fun buildMovieCarousel(title: String, movieList: List<MovieModel>?) {
        if (!movieList.isNullOrEmpty()) {
            TitleHolder_()
                .id("${TitleHolder::class.java.simpleName} ${MovieReviewHolder::class.java.simpleName}")
                .title(title)
                .addTo(this)

            val movieHolders = mutableListOf<MoviePortraitHolder_>()
            movieList.forEach {
                movieHolders.add(
                    it.toPortraitHolder()
                        .id("$title ${it.id}")
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