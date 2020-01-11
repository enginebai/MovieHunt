package com.enginebai.moviehunt.data.repo

import androidx.paging.PageKeyedDataSource
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.remote.MovieListResponse
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieListDataSource(private val listName: String) :
    PageKeyedDataSource<Int, MovieListResponse>(), KoinComponent {

    private val api: MovieApiService by inject()
    // nullable represents end of page
    private var currentPage: Int? = 1

    val networkState = BehaviorSubject.createDefault(NetworkState.IDLE)
    val initLoadState = BehaviorSubject.createDefault(NetworkState.IDLE)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieListResponse>
    ) {
        api.fetchMovieList(listName, currentPage)
            .doOnSubscribe {
                networkState.onNext(NetworkState.LOADING)
                initLoadState.onNext(NetworkState.LOADING)
            }
            .doOnSuccess {
                it.results?.run {
                    callback.onResult(it.results, null, calculateNextPage(it.totalPages))
                }
                networkState.onNext(NetworkState.IDLE)
                initLoadState.onNext(NetworkState.IDLE)
            }
            .doOnError {
                networkState.onNext(NetworkState.ERROR)
                initLoadState.onNext(NetworkState.ERROR)
            }
            .subscribe()
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieListResponse>
    ) {
        if (null == currentPage || NetworkState.LOADING == networkState.value) return
        api.fetchMovieList(listName, currentPage)
            .doOnSubscribe { networkState.onNext(NetworkState.LOADING) }
            .doOnSuccess {
                it.results?.run {
                    callback.onResult(it.results, calculateNextPage(it.totalPages))
                }
                networkState.onNext(NetworkState.IDLE)
            }
            .doOnError { networkState.onNext(NetworkState.ERROR) }
            .subscribe()
    }

    private fun calculateNextPage(totalPage: Int?): Int? {
        if (null != totalPage) {
            if (totalPage > (currentPage ?: 1)) {
                currentPage?.plus(1)
            }
        }
        return currentPage
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieListResponse>
    ) {
        // we don't need this.
    }
}