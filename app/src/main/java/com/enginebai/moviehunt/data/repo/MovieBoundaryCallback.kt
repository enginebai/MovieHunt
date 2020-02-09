package com.enginebai.moviehunt.data.repo

import androidx.paging.PagedList
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.data.local.MovieDao
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.remote.TmdbApiModel
import com.enginebai.moviehunt.data.remote.mapToMovieModels
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieBoundaryCallback(private val movieList: String) :
    PagedList.BoundaryCallback<MovieModel>(), KoinComponent {

    private val movieApi: MovieApiService by inject()
    private val movieDao: MovieDao by inject()
    // nullable represents end of page
    private var currentPage: Int? = 1

    val initLoadState = BehaviorSubject.createDefault<NetworkState>(NetworkState.IDLE)
    val loadMoreState = BehaviorSubject.createDefault<NetworkState>(NetworkState.IDLE)

    override fun onZeroItemsLoaded() {
        currentPage = 1
        movieApi.fetchMovieList(movieList, currentPage).subscribeRemoteDataSource(firstLoad = true)
    }

    override fun onItemAtEndLoaded(itemAtEnd: MovieModel) {
        movieApi.fetchMovieList(movieList, currentPage).subscribeRemoteDataSource(firstLoad = false)
    }

    private fun Single<TmdbApiModel>.subscribeRemoteDataSource(firstLoad: Boolean) {
        this.map {
            val movieList = it.results?.mapToMovieModels() ?: emptyList()
            movieDao.upsertList(movieList)
            calculateNextPage(it.totalPages)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (firstLoad)
                    initLoadState.onNext(NetworkState.LOADING)
                else
                    loadMoreState.onNext(NetworkState.LOADING)
            }.doOnError { initLoadState.onNext(NetworkState.ERROR) }
            .doOnSuccess { initLoadState.onNext(NetworkState.IDLE) }
            .subscribe()
    }

    private fun calculateNextPage(totalPage: Int?): Int? {
        if (null != totalPage) {
            if (totalPage > (currentPage ?: 1)) {
                currentPage = currentPage?.plus(1)
            }
        }
        return currentPage
    }
}