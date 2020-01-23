package com.enginebai.moviehunt.data.repo

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.remote.MovieListResponse
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieListDataSource(private val movieList: String,
                          private val initLoadState: BehaviorSubject<NetworkState>,
                          private val loadMoreState: BehaviorSubject<NetworkState>) :
    PageKeyedDataSource<Int, MovieModel>(), KoinComponent {

    private val api: MovieApiService by inject()
    // nullable represents end of page
    private var currentPage: Int? = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel>
    ) {
        api.fetchMovieList(movieList, currentPage)
            .doOnSubscribe {
                initLoadState.onNext(NetworkState.LOADING)
            }
            .doOnSuccess {
                it.results?.run {
                    callback.onResult(it.results.mapToMovieModels(), null, calculateNextPage(it.totalPages))
                }
                initLoadState.onNext(NetworkState.IDLE)
            }
            .doOnError {
                initLoadState.onNext(NetworkState.ERROR)
            }
            .subscribe()
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieModel>
    ) {
        if (null == currentPage || NetworkState.LOADING == loadMoreState.value) return
        api.fetchMovieList(movieList, currentPage)
            .doOnSubscribe { loadMoreState.onNext(NetworkState.LOADING) }
            .doOnSuccess {
                it.results?.run {
                    callback.onResult(it.results.mapToMovieModels(), calculateNextPage(it.totalPages))
                }
                loadMoreState.onNext(NetworkState.IDLE)
            }
            .doOnError { loadMoreState.onNext(NetworkState.ERROR) }
            .subscribe()
    }

    private fun List<MovieListResponse>.mapToMovieModels(): List<MovieModel> =
        this.map {
            MovieModel(
                id = it.id,
                posterPath = it.posterPath,
                title = it.title,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                releaseDate = it.releaseDate
            )
        }

    private fun calculateNextPage(totalPage: Int?): Int? {
        if (null != totalPage) {
            if (totalPage > (currentPage ?: 1)) {
                currentPage = currentPage?.plus(1)
            }
        }
        return currentPage
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieModel>
    ) {
        // we don't need this.
    }
}

class MovieListDataSourceFactory(private val movieList: String) : DataSource.Factory<Int, MovieModel>() {

    val initLoadState = BehaviorSubject.createDefault<NetworkState>(NetworkState.IDLE)
    val loadMoreState = BehaviorSubject.createDefault<NetworkState>(NetworkState.IDLE)
    var dataSource: MovieListDataSource? = null

    override fun create(): DataSource<Int, MovieModel> {
        dataSource = MovieListDataSource(movieList, initLoadState, loadMoreState)
        return dataSource!!
    }
}