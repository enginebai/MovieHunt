package com.enginebai.moviehunt.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.view.BaseFragment
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.getPosterUrlWithLargeSize
import com.enginebai.moviehunt.data.remote.CastListing
import com.enginebai.moviehunt.data.remote.Review
import com.enginebai.moviehunt.data.remote.Video
import com.enginebai.moviehunt.data.repo.MovieRepo
import com.enginebai.moviehunt.utils.loadImage
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject

class MovieDetailFragment : BaseFragment(), MovieDetailClickListener {

    companion object {
        const val FIELD_MOVIE_ID = "movieId"

        fun newInstance(movieId: String): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = bundleOf(FIELD_MOVIE_ID to movieId)
            }
        }
    }

    private val detailViewMovieModel by viewModel<MovieDetailViewModel>()
    private val detailController by lazy { MovieDetailController(requireContext(), this) }

    override fun getLayoutId() = R.layout.fragment_movie_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onTrailerClicked(trailerVideo: String) {
        val playVideoIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(trailerVideo))
        activity?.startActivity(playVideoIntent)
    }
}

class MovieDetailViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    private val _movieDetail = MutableLiveData<MovieModel>()
    val movieDetail: LiveData<MovieModel> = _movieDetail
    private val _movieVideos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _movieVideos
    private val _movieReviews = MutableLiveData<List<Review>>()
    val review: LiveData<Review?> = Transformations.map(_movieReviews) {
        // TODO: find the best review
        it.firstOrNull()
    }
    private val _movieCasts = MutableLiveData<List<CastListing.Cast>>()
    val casts: LiveData<List<CastListing.Cast>> = _movieCasts
    private val _similarMovies = MutableLiveData<List<MovieModel>>()
    val similarMovies: LiveData<List<MovieModel>> = _similarMovies
    private val _recommendationMovies = MutableLiveData<List<MovieModel>>()
    val recommendationMovies: LiveData<List<MovieModel>> = _recommendationMovies

    fun fetchMovieDetail(id: String) {
        movieRepo.fetchMovieDetail(id)
            .subscribeOn(Schedulers.io())
            .subscribe()
            .disposeOnCleared()

        movieRepo.fetchMovieVideos(id)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                _movieVideos.postValue(it)
            }
            .subscribe()
            .disposeOnCleared()

        movieRepo.fetchMovieReviews(id)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                _movieReviews.postValue(it)
            }
            .subscribe()
            .disposeOnCleared()

        movieRepo.fetchMovieCasts(id)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                _movieCasts.postValue(it)
            }
            .subscribe()
            .disposeOnCleared()

        movieRepo.fetchSimilarMovies(id)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                _similarMovies.postValue(it)
            }
            .subscribe()
            .disposeOnCleared()

        movieRepo.fetchRecommendationMovies(id)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                _recommendationMovies.postValue(it)
            }
            .subscribe()
            .disposeOnCleared()

        movieRepo.getMovieDetail(id)
            .doOnNext {
                _movieDetail.postValue(it)
            }.subscribe()
            .disposeOnCleared()
    }
}