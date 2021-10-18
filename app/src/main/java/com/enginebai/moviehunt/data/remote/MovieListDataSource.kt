package com.enginebai.moviehunt.data.remote

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieModelMapper.toMovieModel
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MovieListDataSource(
        private val category: MovieCategory,
        private val initLoadState: BehaviorSubject<NetworkState>,
        private val loadMoreState: BehaviorSubject<NetworkState>
) : PageKeyedDataSource<Int, MovieModel>(), KoinComponent {

    private val api: MovieApiService by inject()
    private var currentPage: Int = -1

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, MovieModel>
    ) {
        currentPage = 1
        api.fetchMovieList(category.key, currentPage)
                .doOnSubscribe { initLoadState.onNext(NetworkState.LOADING) }
                .doOnSuccess {
                    it.results?.run {
                        callback.onResult(
                                this.mapToMovieModels(),
                                null,
                                calculateNextPage(it.totalPages)
                        )
                    }
                    initLoadState.onNext(NetworkState.IDLE)
                }
                .doOnError { initLoadState.onNext(NetworkState.ERROR) }
                .subscribe()
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        if (-1 == params.key || NetworkState.LOADING == loadMoreState.value) return
        api.fetchMovieList(category.key, params.key)
                .doOnSubscribe { loadMoreState.onNext(NetworkState.LOADING) }
                .doOnSuccess {
                    it.results?.run {
                        callback.onResult(this.mapToMovieModels(), calculateNextPage(it.totalPages))
                    }
                    loadMoreState.onNext(NetworkState.IDLE)
                }
                .doOnError { loadMoreState.onNext(NetworkState.ERROR) }
                .subscribe()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        // we don't need this
    }

    private fun calculateNextPage(totalPage: Int?): Int {
        totalPage?.run {
            currentPage = if (currentPage in 1 until totalPage) {
                currentPage.plus(1)
            } else {
                -1
            }
        }
        return currentPage
    }

    private fun List<MovieListResponse>.mapToMovieModels(): List<MovieModel> =
            this.map { it.toMovieModel() }
}

class MovieListDataSourceFactory(private val category: MovieCategory) :
        DataSource.Factory<Int, MovieModel>() {

    val initLoadState = BehaviorSubject.createDefault(NetworkState.IDLE)
    val loadMoreState = BehaviorSubject.createDefault(NetworkState.IDLE)
    var dataSource: MovieListDataSource? = null

    override fun create(): DataSource<Int, MovieModel> {
        dataSource = MovieListDataSource(category, initLoadState, loadMoreState)
        return dataSource!!
    }

}