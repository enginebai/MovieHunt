package com.enginebai.moviehunt.ui.movie.list

import androidx.paging.PagedList
import com.enginebai.base.utils.Listing
import com.enginebai.base.utils.NetworkState
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.inject

class MovieListViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    // v1 architecture (from Google Android Architecture Components PagingWithNetworkSample Project)
    private val listName = BehaviorSubject.create<String>()
    private val fetchDataSource: Observable<Listing<MovieModel>> = listName
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

    fun fetchMovieList(category: String) {
        listName.onNext(category)
    }

    fun refresh() {
        fetchDataSource
            .map { it.refresh }
            .doOnNext { it.invoke() }
            .subscribe()
            .disposeOnCleared()
    }

    // v2 architecture
    fun fetchList(listName: String): Listing<MovieModel> {
        return movieRepo.fetchMovieList(listName)
    }
}