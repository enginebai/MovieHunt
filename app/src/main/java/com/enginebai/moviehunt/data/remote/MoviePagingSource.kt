package com.enginebai.moviehunt.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.enginebai.moviehunt.ui.list.MovieCategory
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ApiPagingSource<T : Any> : PagingSource<Int, T>(), KoinComponent {

    private val api: MovieApiService by inject()

    abstract suspend fun apiFetch(page: Int): TmdbApiResponse<T>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val currentPage = params.key ?: 1
            val tmdbApiResponse = apiFetch(currentPage)
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
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
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
    override suspend fun apiFetch(page: Int) = api.fetchMovieList(category.key, page)
}

class MovieReviewPagingSource(
    private val movieId: String
) : ApiPagingSource<Review>() {
    private val api: MovieApiService by inject()
    override suspend fun apiFetch(page: Int) = api.fetchMovieReviews(movieId, page)
}