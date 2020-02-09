package com.enginebai.moviehunt.data.repo

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.enginebai.base.utils.Listing
import com.enginebai.moviehunt.data.local.MovieDao
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

const val DEFAULT_PAGE_SIZE = 10

interface MovieRepo {
    fun fetchMovieList(movieList: String, pageSize: Int = DEFAULT_PAGE_SIZE): Listing<MovieModel>
    fun getMovieList(movieList: String, pageSize: Int = DEFAULT_PAGE_SIZE): Listing<MovieModel>
    fun fetchMovieDetail(movieId: String): Single<MovieModel>
}

class MovieRepoImpl : MovieRepo, KoinComponent {

    private val movieApi: MovieApiService by inject()
    private val movieDao: MovieDao by inject()

    override fun fetchMovieList(movieList: String, pageSize: Int): Listing<MovieModel> {
        val dataSourceFactory = MovieListDataSourceFactory(movieList)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(pageSize)
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

    override fun getMovieList(movieList: String, pageSize: Int): Listing<MovieModel> {
        val dataSourceFactory = movieDao.queryMovieListObservable()
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        val boundaryCallback = MovieBoundaryCallback(movieList)
        val pagedList = RxPagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallback)
            .buildObservable()
        return Listing(
            pagedList = pagedList,
            refreshState = boundaryCallback.initLoadState,
            loadMoreState = boundaryCallback.loadMoreState,
            refresh = { boundaryCallback.onZeroItemsLoaded() }
        )
    }

    override fun fetchMovieDetail(movieId: String): Single<MovieModel> {
        return movieApi.fetchMovieDetail(movieId)
            .map { response ->
                MovieModel(
                    id = response.id,
                    posterPath = response.posterPath,
                    title = response.title,
                    voteAverage = response.voteAverage,
                    voteCount = response.voteCount,
                    overview = response.overview,
                    releaseDate = response.releaseDate,
                    genreList = response.genreList,
                    runtime = response.runtime
                )
            }
    }
}