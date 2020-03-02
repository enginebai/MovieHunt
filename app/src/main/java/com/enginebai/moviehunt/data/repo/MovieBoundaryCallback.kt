package com.enginebai.moviehunt.data.repo

import androidx.paging.PagedList
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.data.local.MovieDao
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.remote.TmdbApiModel
import com.enginebai.moviehunt.ui.movie.home.MovieCategory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class MovieBoundaryCallback(private val category: MovieCategory,
                            private val nextPageIndex: NextPageIndex) :
    PagedList.BoundaryCallback<MovieModel>(), KoinComponent {

    private val movieApi: MovieApiService by inject()
    private val movieDao: MovieDao by inject()

    val initLoadState = BehaviorSubject.createDefault<NetworkState>(NetworkState.IDLE)
    val loadMoreState = BehaviorSubject.createDefault<NetworkState>(NetworkState.IDLE)

    override fun onZeroItemsLoaded() {
        if (initLoadState.value == NetworkState.LOADING) return
        nextPageIndex.setNextPageIndex(category.key, 1)
        Timber.tag("qwer").d("$category Current page ${nextPageIndex.getNextPageIndex(category.key)} onZeroItemsLoaded()")
        movieApi.fetchMovieList(category.key, nextPageIndex.getNextPageIndex(category.key) ?: 1).subscribeRemoteDataSource(firstLoad = true)
    }

    override fun onItemAtEndLoaded(itemAtEnd: MovieModel) {
        if (loadMoreState.value == NetworkState.LOADING ||
                null == nextPageIndex.getNextPageIndex(category.key)) return
        Timber.tag("qwer").d("$category Next page ${nextPageIndex.getNextPageIndex(category.key)} onItemAtEndLoaded()")
        movieApi.fetchMovieList(category.key, nextPageIndex.getNextPageIndex(category.key)!!).subscribeRemoteDataSource(firstLoad = false)
    }

    private fun Single<TmdbApiModel>.subscribeRemoteDataSource(firstLoad: Boolean) {
        this.map {
            movieDao.upsertMovieListResponses(category, it.results ?: emptyList())
            calculateNextPage(it.totalPages)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { changeLoadState(firstLoad, NetworkState.LOADING) }
            .doOnError { changeLoadState(firstLoad, NetworkState.ERROR) }
            .doOnSuccess { changeLoadState(firstLoad, NetworkState.IDLE)}
            .subscribe()
    }

    private fun changeLoadState(firstLoad: Boolean, state: NetworkState) {
        if (firstLoad)
            initLoadState.onNext(state)
        else
            loadMoreState.onNext(state)
    }

    private fun calculateNextPage(totalPage: Int?): Int? {
        if (null != totalPage) {
            val currentPage = nextPageIndex.getNextPageIndex(category.key)
            if (totalPage > (currentPage ?: 1)) {
                nextPageIndex.setNextPageIndex(category.key, currentPage?.plus(1))
            }
        }
        return nextPageIndex.getNextPageIndex(category.key)
    }
}