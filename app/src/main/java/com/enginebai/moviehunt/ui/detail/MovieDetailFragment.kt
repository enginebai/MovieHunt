package com.enginebai.moviehunt.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enginebai.base.view.BaseFragment
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.Video
import com.enginebai.moviehunt.data.repo.MovieRepo
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject

class MovieDetailFragment : BaseFragment() {

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
        detailViewMovieModel.movieDetail.observe(viewLifecycleOwner, {
            Log.d(this.toString(), it.toString())
        })
        detailViewMovieModel.videos.observe(viewLifecycleOwner, {
            Log.d(this.toString(), it.toString())
        })
    }
}

class MovieDetailViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    private val _movieDetail = MutableLiveData<MovieModel>()
    val movieDetail: LiveData<MovieModel> = _movieDetail
    private val _movieVideos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _movieVideos

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

        movieRepo.getMovieDetail(id)
                .doOnNext {
                    _movieDetail.postValue(it)
                }.subscribe()
                .disposeOnCleared()
    }
}