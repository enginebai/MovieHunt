package com.enginebai.moviehunt.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.CastListing
import com.enginebai.moviehunt.data.remote.Review
import com.enginebai.moviehunt.data.remote.Video
import com.enginebai.moviehunt.data.repo.MovieRepo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class MovieDetailViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    private val _movieDetail = MutableLiveData<MovieModel>()
    val movieDetail: LiveData<MovieModel> = _movieDetail
    private val _movieVideos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _movieVideos
    private val _movieReviews = MutableLiveData<List<Review>>()
    val review: LiveData<Review?> = Transformations.map(_movieReviews) {
        it.findBestReview()
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

        viewModelScope.launch {
            _movieReviews.value = movieRepo.fetchMovieReviews(id)
        }

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

    private fun List<Review>.findBestReview(): Review? {
        return filter {
            !it.content.isNullOrBlank() &&
                    !it.author?.avatarPath.isNullOrBlank() &&
                    it.author?.rating != null
        }.maxByOrNull { it.author!!.rating!! }
            ?: this.firstOrNull {
                !it.content.isNullOrBlank() &&
                        !it.author?.avatarPath.isNullOrBlank()
            }
    }
}