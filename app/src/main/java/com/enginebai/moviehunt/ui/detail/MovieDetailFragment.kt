package com.enginebai.moviehunt.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.data.remote.ImageApi
import com.enginebai.moviehunt.data.remote.ImageSize
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MovieHuntTheme
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.holders.MovieCastWidget
import com.enginebai.moviehunt.ui.detail.holders.MovieInfoWidget
import com.enginebai.moviehunt.ui.detail.holders.MovieTrailerWidget
import com.enginebai.moviehunt.ui.reviews.MovieReviewsFragment
import com.enginebai.moviehunt.ui.widgets.MovieReviewWidget
import com.enginebai.moviehunt.ui.widgets.TitleWidget
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import com.enginebai.moviehunt.utils.openFragment
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.view_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : BaseFragment(), MovieClickListener {

    companion object {
        const val FIELD_MOVIE_ID = "movieId"

        fun newInstance(movieId: String): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = bundleOf(FIELD_MOVIE_ID to movieId)
            }
        }
    }

    private val detailViewMovieModel by viewModel<MovieDetailViewModel>()

    override fun getLayoutId() = R.layout.fragment_movie_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewMovieModel.fetchMovieDetail(arguments?.getString(FIELD_MOVIE_ID)!!)
        composeView.apply {
            setContent {
                MovieHuntTheme {
                    MovieDetail(
                        detailViewMovieModel, onBackButtonClicked = {
                            activity?.onBackPressed()
                        }, ::onTrailerClicked,
                        ::onReviewSeeAllClicked,
                        this@MovieDetailFragment
                    )
                }
            }
        }
    }

    private fun onTrailerClicked(trailerVideo: String) {
        val playVideoIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(trailerVideo))
        activity?.startActivity(playVideoIntent)
    }

    private fun onReviewSeeAllClicked(movieId: String) {
        activity?.openFragment(MovieReviewsFragment.newInstance(movieId), true)
    }

    override fun onMovieClicked(movieId: String) {
        activity?.openFragment(newInstance(movieId), true)
    }
}

@Composable
fun MovieDetail(
    viewModel: MovieDetailViewModel,
    onBackButtonClicked: () -> Unit,
    trailerClickListener: (String) -> Unit,
    reviewSeeAllClickListener: (String) -> Unit,
    movieClickListener: MovieClickListener
) {
    val detail by viewModel.movieDetail.observeAsState()
    val videos by viewModel.videos.observeAsState()
    val review by viewModel.review.observeAsState()
    val casts by viewModel.casts.observeAsState()
    val recommendationMovies by viewModel.recommendationMovies.observeAsState()
    val similarMovies by viewModel.similarMovies.observeAsState()

    val horizontalArrangement = Arrangement.spacedBy(8.dp)
    val horizontalContentPadding =
        PaddingValues(horizontal = MHDimensions.pagePadding.dp, vertical = 12.dp)
    val paddingAboveTitle = 16.dp

    @Composable
    fun buildMovieCarousel(title: String, movieList: List<MovieModel>?) {
        if (!movieList.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(paddingAboveTitle))
            TitleWidget(title = title)
            LazyRow(
                horizontalArrangement = horizontalArrangement,
                contentPadding = horizontalContentPadding
            ) {
                items(movieList) { movie ->
                    movie.PortraitWidget {
                        movieClickListener.onMovieClicked(it)
                    }
                }
            }
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        detail?.let { detail ->
            MovieInfoWidget(
                posterUrl = ImageApi.getFullUrl(detail.posterPath, ImageSize.W780),
                movieName = detail.title,
                rating = detail.display5StarsRating(),
                ratingTotalCountText = detail.displayVoteCount(),
                genres = detail.genreList?.map { it.name }?.joinToString(),
                releaseDateText = detail.releaseDate?.format(),
                runtimeText = detail.displayDuration(),
                overview = detail.overview,
                onBackButtonClicked = onBackButtonClicked
            )
        }

        if (!videos.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(paddingAboveTitle))
            TitleWidget(title = stringResource(id = R.string.title_trailers))
            LazyRow(
                horizontalArrangement = horizontalArrangement,
                contentPadding = horizontalContentPadding
            ) {
                items(videos!!) { video ->
                    MovieTrailerWidget(
                        thumbnail = video.youtubeThumbnail,
                        trailerUrl = video.youtubeVideo,
                    ) {
                        trailerClickListener.invoke(video.youtubeVideo)
                    }
                }
            }
        }

        review?.let { review ->
            Spacer(modifier = Modifier.height(paddingAboveTitle))
            TitleWidget(title = stringResource(id = R.string.title_reviews), onClickListener = {
                detail?.id?.let { reviewSeeAllClickListener.invoke(it) }
            })
            Spacer(modifier = Modifier.height(4.dp))
            MovieReviewWidget(
                avatar = review.author?.getAvatarFullPath(),
                name = review.author?.username,
                rating = review.author?.rating,
                comment = review.content,
                createdAtDateText = review.createdAt?.format()
            )
        }

        if (!casts.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(paddingAboveTitle))
            TitleWidget(title = stringResource(id = R.string.title_casts))
            LazyRow(
                horizontalArrangement = horizontalArrangement,
                contentPadding = horizontalContentPadding
            ) {
                items(casts!!) { cast ->
                    MovieCastWidget(
                        avatar = cast.getAvatarFullPath(),
                        actorName = cast.actorName,
                        character = cast.character
                    )

                }
            }
        }

        buildMovieCarousel(
            title = stringResource(id = R.string.title_recommendation_movies),
            movieList = recommendationMovies
        )
        buildMovieCarousel(
            title = stringResource(id = R.string.title_similar_movies),
            movieList = similarMovies
        )
    }
}

