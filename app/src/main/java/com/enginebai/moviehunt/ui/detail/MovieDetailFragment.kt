package com.enginebai.moviehunt.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.display5StarsRating
import com.enginebai.moviehunt.data.local.displayDuration
import com.enginebai.moviehunt.data.local.displayVoteCount
import com.enginebai.moviehunt.data.remote.ImageApi
import com.enginebai.moviehunt.data.remote.ImageSize
import com.enginebai.moviehunt.resources.MovieHuntTheme
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.holders.MovieCastWidgetPreview
import com.enginebai.moviehunt.ui.detail.holders.MovieInfoWidget
import com.enginebai.moviehunt.ui.detail.holders.MovieInfoWidgetPreview
import com.enginebai.moviehunt.ui.detail.holders.MovieTrailerWidgetPreview
import com.enginebai.moviehunt.ui.reviews.MovieReviewsFragment
import com.enginebai.moviehunt.ui.widgets.MoviePortraitWidgetPreview
import com.enginebai.moviehunt.ui.widgets.MovieReviewWidget
import com.enginebai.moviehunt.ui.widgets.MovieReviewWidgetPreview
import com.enginebai.moviehunt.ui.widgets.TitleWidgetPreview
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
    private val detailController by lazy {
        MovieDetailController(
            requireContext(),
            ::onTrailerClicked,
            ::onReviewSeeAllClicked,
            this
        )
    }

    override fun getLayoutId() = R.layout.fragment_movie_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupToolbar()
        detailViewMovieModel.fetchMovieDetail(arguments?.getString(FIELD_MOVIE_ID)!!)
        composeView.apply {
            setContent {
                MovieHuntTheme {
                    MovieDetail(detailViewMovieModel)
                }
            }
        }
    }

    private fun setupToolbar() {
        buttonBack.setOnClickListener {
            activity?.onBackPressed()
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
fun MovieDetail(viewModel: MovieDetailViewModel) {
    val detail by viewModel.movieDetail.observeAsState()
    val review by viewModel.review.observeAsState()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        detail?.let { detail ->
            MovieInfoWidget(
                posterUrl = ImageApi.getFullUrl(detail.posterPath, ImageSize.W780),
                movieName = detail.title,
                rating = detail.display5StarsRating(),
                ratingTotalCountText = detail.displayVoteCount(),
                genres = detail.genreList?.map { it.name }?.joinToString(","),
                releaseDateText = detail.releaseDate?.format(),
                runtimeText = detail.displayDuration(),
                overview = detail.overview
            )
        }

        MovieTrailerWidgetPreview()

        review?.let { review ->
            MovieReviewWidget(
                avatar = review.author?.getAvatarFullPath(),
                name = review.author?.username,
                rating = review.author?.rating,
                comment = review.content,
                createdAtDateText = review.createdAt?.format()
            )
        }
        MovieCastWidgetPreview()
        MoviePortraitWidgetPreview()
    }
}

