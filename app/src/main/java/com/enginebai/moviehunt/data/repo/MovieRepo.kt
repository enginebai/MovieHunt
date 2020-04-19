package com.enginebai.moviehunt.data.repo

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.remote.MovieListDataSourceFactory
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

const val DEFAULT_PAGE_SIZE = 10

interface MovieRepo {
    fun fetchMovieList(category: MovieCategory, pageSize: Int = DEFAULT_PAGE_SIZE): Observable<PagedList<MovieModel>>
}

class MovieRepoImpl : MovieRepo, KoinComponent {

    override fun fetchMovieList(
        category: MovieCategory,
        pageSize: Int
    ): Observable<PagedList<MovieModel>> {
        val dataSourceFactory = MovieListDataSourceFactory(category)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(DEFAULT_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        return RxPagedListBuilder(dataSourceFactory, pagedListConfig)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
    }
}