package com.enginebai.moviehunt.data.repo

import androidx.paging.PagedList
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.data.local.MovieDao
import com.enginebai.moviehunt.data.local.MovieDatabase
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.remote.MovieListResponse
import com.enginebai.moviehunt.data.remote.TmdbApiResponse
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MovieBoundaryCallback(
    private val category: MovieCategory,
    private val pageSize: Int,
    private val nextPageIndex: NextPageIndex
) : PagedList.BoundaryCallback<MovieModel>(), KoinComponent {

    private val movieApi: MovieApiService by inject()
    private val movieDao: MovieDao by inject()
    private val movieDb: MovieDatabase by inject()

    val initLoadState = BehaviorSubject.createDefault(NetworkState.IDLE)
    val loadMoreState = BehaviorSubject.createDefault(NetworkState.IDLE)

    override fun onZeroItemsLoaded() {
        if (initLoadState.value == NetworkState.LOADING) return
        nextPageIndex.setNextPageIndex(category.key, 1)
        movieApi.fetchMovieList(category.key, nextPageIndex.getNextPageIndex(category.key))
            .subscribeRemoteDataSource(firstLoad = true)
    }

    override fun onItemAtEndLoaded(itemAtEnd: MovieModel) {
        if (loadMoreState.value == NetworkState.LOADING ||
            -1 == nextPageIndex.getNextPageIndex(category.key)
        ) return
        movieApi
            .fetchMovieList(category.key, nextPageIndex.getNextPageIndex(category.key))
            .subscribeRemoteDataSource()
    }

    private fun Single<TmdbApiResponse<MovieListResponse>>.subscribeRemoteDataSource(firstLoad: Boolean = false) {
        this.map {
            movieDb.runInTransaction {
                if (firstLoad)
                    movieDao.deleteMovieList(category)
                movieDao.upsertMovieListResponse(
                    category = category,
                    list = it.results ?: emptyList(),
                    pageSize = pageSize,
                    currentPage = nextPageIndex.getNextPageIndex(category.key)
                )
            }
            calculateNextPage(it.totalPages)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { changeLoadState(firstLoad, NetworkState.LOADING) }
            .doOnError { changeLoadState(firstLoad, NetworkState.ERROR) }
            .doOnSuccess { changeLoadState(firstLoad, NetworkState.IDLE) }
            .subscribe()
    }

    private fun calculateNextPage(totalPage: Int?): Int {
        if (null != totalPage) {
            val currentPage = nextPageIndex.getNextPageIndex(category.key)
            if (currentPage in 1 until totalPage) {
                nextPageIndex.setNextPageIndex(category.key, currentPage.plus(1))
            } else {
                nextPageIndex.setNextPageIndex(category.key, -1)
            }
        }
        return nextPageIndex.getNextPageIndex(category.key)
    }

    private fun changeLoadState(firstLoad: Boolean, state: NetworkState) {
        if (firstLoad) initLoadState.onNext(state)
        else loadMoreState.onNext(state)
    }
}