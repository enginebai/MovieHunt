package com.enginebai.moviehunt.data.repo

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.enginebai.base.utils.Listing
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.remote.MovieListDataSourceFactory
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

const val DEFAULT_PAGE_SIZE = 10

interface MovieRepo {
    fun fetchMovieList(category: MovieCategory, pageSize: Int = DEFAULT_PAGE_SIZE): Listing<MovieModel>
	fun fetchMovieDetail(movieId: String): Single<MovieModel>
}

class MovieRepoImpl : MovieRepo, KoinComponent {

	private val movieApi: MovieApiService by inject()

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