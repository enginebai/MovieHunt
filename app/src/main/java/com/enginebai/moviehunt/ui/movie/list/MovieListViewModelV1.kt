package com.enginebai.moviehunt.ui.movie.list

import androidx.paging.PagedList
import com.enginebai.base.utils.Listing
import com.enginebai.base.utils.NetworkState
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import com.enginebai.moviehunt.ui.movie.home.MovieCategory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.inject

class MovieListViewModelV1 : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    // v1 architecture (from Google Android Architecture Components PagingWithNetworkSample Project)
    private val movieCategory = BehaviorSubject.create<MovieCategory>()
    private val fetchDataSource: Observable<Listing<MovieModel>> = movieCategory
        .map {
            movieRepo.fetchMovieList(it)
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .cache()

    val movieList: Observable<PagedList<MovieModel>>
        get() = fetchDataSource.flatMap { it.pagedList }
    val refreshState: Observable<NetworkState>
        get() = fetchDataSource.flatMap { it.refreshState }
    val networkState: Observable<NetworkState>
        get() = fetchDataSource.flatMap { it.loadMoreState }

    fun fetchMovieList(category: MovieCategory) {
        movieCategory.onNext(category)
    }

    fun refresh() {
        fetchDataSource
            .map { it.refresh }
            .doOnNext { it.invoke() }
            .subscribe()
            .disposeOnCleared()
    }
}