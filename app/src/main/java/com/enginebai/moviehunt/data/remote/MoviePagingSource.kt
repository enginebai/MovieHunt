package com.enginebai.moviehunt.data.remote

import androidx.paging.rxjava2.RxPagingSource
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
}