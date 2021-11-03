package com.enginebai.moviehunt.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.getPosterUrlWithLargeSize
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.reviews.MovieReviewsFragment
import com.enginebai.moviehunt.utils.loadImage
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
        setupToolbar()
        detailViewMovieModel.fetchMovieDetail(arguments?.getString(FIELD_MOVIE_ID)!!)
        listContent.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        listContent.setController(detailController)

        detailViewMovieModel.movieDetail.observe(viewLifecycleOwner, {
            Log.d(this.javaClass.simpleName, "Detail $it")
            imagePoster.loadImage(it.getPosterUrlWithLargeSize())
            detailController.detail = it
        })
        detailViewMovieModel.videos.observe(viewLifecycleOwner, {
            Log.d(this.javaClass.simpleName, "Videos $it")
            detailController.videos = it
        })
        detailViewMovieModel.review.observe(viewLifecycleOwner, {
            Log.d(this.javaClass.simpleName, "Reviews $it")
            detailController.review = it
        })
        detailViewMovieModel.casts.observe(viewLifecycleOwner, {
            Log.d(this.javaClass.simpleName, "Casts $it")
            detailController.casts = it
        })
        detailViewMovieModel.similarMovies.observe(viewLifecycleOwner, {
            detailController.similarMovies = it
        })
        detailViewMovieModel.recommendationMovies.observe(viewLifecycleOwner, {
            detailController.recommendationMovies = it
        })
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

