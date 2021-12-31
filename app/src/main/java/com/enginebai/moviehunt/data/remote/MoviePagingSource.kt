package com.enginebai.moviehunt.data.remote

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ApiPagingSource<T : Any> : RxPagingSource<Int, T>(), KoinComponent {

    private val api: MovieApiService by inject()

    abstract fun apiFetch(page: Int): Single<TmdbApiResponse<T>>

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, T>> {
        val currentPage = params.key ?: 1
        return apiFetch(currentPage)
            .subscribeOn(Schedulers.io())
            .onErrorReturn { TmdbApiResponse() }
            .map { tmdbApiResponse ->
                tmdbApiResponse.results?.let { list ->
                    val nextKey = if (list.isEmpty()) null else currentPage + 1
                    LoadResult.Page(
                        data = list,
                        prevKey = null,
                        nextKey = nextKey
                    )
                } ?: let {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            }
        // TODO: error handling
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

class MovieListPagingSource(
    private val category: MovieCategory
) : ApiPagingSource<MovieListResponse>() {
    private val api: MovieApiService by inject()
    override fun apiFetch(page: Int) = api.fetchMovieList(category.key, page)
}

class MovieReviewPagingSource(
    private val movieId: String
) : ApiPagingSource<Review>() {
    private val api: MovieApiService by inject()
    override fun apiFetch(page: Int) = api.fetchMovieReviews(movieId, page)
}