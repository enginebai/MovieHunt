package com.enginebai.moviehunt.data.remote

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.enginebai.base.utils.NetworkState
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Deprecated("Use MoviePagingSource")
abstract class ApiPageKeyedDataSource<T : Any>(
    private val initLoadState: BehaviorSubject<NetworkState>,
    private val loadMoreState: BehaviorSubject<NetworkState>
) : PageKeyedDataSource<Int, T>(), KoinComponent {

    private val api: MovieApiService by inject()
    private var currentPage: Int = -1

    abstract fun apiFetch(page: Int): Single<TmdbApiResponse<T>>

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        currentPage = 1
        apiFetch(currentPage)
            .doOnSubscribe { initLoadState.onNext(NetworkState.LOADING) }
            .doOnSuccess {
                it.results?.run {
                    callback.onResult(
                        this,
                        null,
                        calculateNextPage(it.totalPages)
                    )
                }
                initLoadState.onNext(NetworkState.IDLE)
            }
            .doOnError { initLoadState.onNext(NetworkState.ERROR) }
            .subscribe()
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        if (-1 == params.key || NetworkState.LOADING == loadMoreState.value) return
        apiFetch(params.key)
            .doOnSubscribe { loadMoreState.onNext(NetworkState.LOADING) }
            .doOnSuccess {
                it.results?.run {
                    callback.onResult(this, calculateNextPage(it.totalPages))
                }
                loadMoreState.onNext(NetworkState.IDLE)
            }
            .doOnError { loadMoreState.onNext(NetworkState.ERROR) }
            .subscribe()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
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
}

abstract class ApiPageKeyedDataSourceFactory<T : Any> : DataSource.Factory<Int, T>() {
    val initLoadState = BehaviorSubject.createDefault(NetworkState.IDLE)
    val loadMoreState = BehaviorSubject.createDefault(NetworkState.IDLE)

    var dataSource: DataSource<Int, T>? = null
}

class MovieReviewsDataSource(
    private val movieId: String,
    initLoadState: BehaviorSubject<NetworkState>,
    loadMoreState: BehaviorSubject<NetworkState>
) : ApiPageKeyedDataSource<Review>(initLoadState, loadMoreState) {
    private val api: MovieApiService by inject()

    override fun apiFetch(page: Int) = api.fetchMovieReviews(movieId, page)
}

class MovieListDataSource(
    private val category: MovieCategory,
    initLoadState: BehaviorSubject<NetworkState>,
    loadMoreState: BehaviorSubject<NetworkState>
) : ApiPageKeyedDataSource<MovieListResponse>(initLoadState, loadMoreState) {

    private val api: MovieApiService by inject()

    override fun apiFetch(page: Int) = api.fetchMovieList(category.key, page)
}

class MovieListDataSourceFactory(private val category: MovieCategory) :
   ApiPageKeyedDataSourceFactory<MovieListResponse>() {

    override fun create(): DataSource<Int, MovieListResponse> {
        dataSource = MovieListDataSource(category, initLoadState, loadMoreState)
        return dataSource!!
    }
}

class MovieReviewsDataSourceFactory(private val movieId: String) :
    ApiPageKeyedDataSourceFactory<Review>() {

    override fun create(): DataSource<Int, Review> {
        dataSource = MovieReviewsDataSource(movieId, initLoadState, loadMoreState)
        return dataSource!!
    }
}