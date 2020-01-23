package com.enginebai.moviehunt.data.repo

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.enginebai.base.utils.Listing
import com.enginebai.moviehunt.data.local.MovieModel
import io.reactivex.schedulers.Schedulers

interface MovieRepo {
    fun fetchMovieList(movieList: String): Listing<MovieModel>
}

class MovieRepoImpl : MovieRepo {

    override fun fetchMovieList(movieList: String): Listing<MovieModel> {
        val dataSourceFactory = MovieListDataSourceFactory(movieList)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        val pagedList = RxPagedListBuilder(dataSourceFactory, pagedListConfig)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
        return Listing(
            pagedList = pagedList,
            refreshState = dataSourceFactory.initLoadState,
            loadMoreState = dataSourceFactory.loadMoreState,
            refresh = { dataSourceFactory.dataSource?.invalidate() }
        )
    }
}