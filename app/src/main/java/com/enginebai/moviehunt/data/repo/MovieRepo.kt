package com.enginebai.moviehunt.data.repo

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.enginebai.base.utils.Listing
import com.enginebai.moviehunt.data.local.MovieDao
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.remote.MovieListDataSourceFactory
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

const val DEFAULT_PAGE_SIZE = 5

interface MovieRepo {
    fun fetchMovieList(
        category: MovieCategory,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Listing<MovieModel>

    fun getMovieList(
        category: MovieCategory,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Listing<MovieModel>

    fun fetchMovieDetail(movieId: String): Single<MovieModel>
}

class MovieRepoImpl : MovieRepo, KoinComponent {

    private val movieApi: MovieApiService by inject()
    private val movieDao: MovieDao by inject()
    private val nextPageIndex: NextPageIndex by lazy { IncrementalNextPage() }

    override fun fetchMovieList(
        category: MovieCategory,
        pageSize: Int
    ): Listing<MovieModel> {
        val dataSourceFactory = MovieListDataSourceFactory(category)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(DEFAULT_PAGE_SIZE)
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

    override fun getMovieList(category: MovieCategory, pageSize: Int): Listing<MovieModel> {
        val dataSourceFactory = movieDao.queryMovieListDataSource(category)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(pageSize)
            .build()
        val boundaryCallback = MovieBoundaryCallback(category, pageSize, nextPageIndex)
        val pagedList = RxPagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallback)
            .buildObservable()
        return Listing(
            pagedList = pagedList,
            refreshState = boundaryCallback.initLoadState,
            loadMoreState = boundaryCallback.loadMoreState,
            refresh = {
                boundaryCallback.onZeroItemsLoaded()
            }
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