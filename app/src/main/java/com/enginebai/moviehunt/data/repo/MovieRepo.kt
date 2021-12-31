package com.enginebai.moviehunt.data.repo

import androidx.paging.*
import androidx.paging.rxjava2.flowable
import com.enginebai.base.utils.Listing
import com.enginebai.moviehunt.data.local.MovieDao
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.*
import com.enginebai.moviehunt.data.remote.MovieModelMapper.toMovieModel
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val DEFAULT_PAGE_SIZE = 5

interface MovieRepo {

    fun fetchMoviePagingData(
            category: MovieCategory,
            pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flowable<PagingData<MovieListResponse>>

    fun getMoviePagedListing(
            category: MovieCategory,
            pageSize: Int = DEFAULT_PAGE_SIZE
    ): Listing<MovieModel>

    fun fetchMovieList(
            category: MovieCategory,
            page: Int? = 1
    ): Single<List<MovieModel>>

    fun fetchMovieDetail(movieId: String): Completable
    fun getMovieDetail(movieId: String): Observable<MovieModel>
    fun fetchMovieVideos(movieId: String): Single<List<Video>>
    fun fetchMovieReviews(movieId: String): Single<List<Review>>
    fun fetchMovieReviewPagingData(
        movieId: String,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flowable<PagingData<Review>>
    fun fetchMovieCasts(movieId: String): Single<List<CastListing.Cast>>
    fun fetchSimilarMovies(movieId: String): Single<List<MovieModel>>
    fun fetchRecommendationMovies(movieId: String): Single<List<MovieModel>>
}

class MovieRepoImpl : MovieRepo, KoinComponent {

    private val movieApi: MovieApiService by inject()
    private val movieDao: MovieDao by inject()
    private val nextPageIndex: NextPageIndex by lazy { IncrementalNextPage() }

    override fun fetchMoviePagingData(
            category: MovieCategory,
            pageSize: Int
    ): Flowable<PagingData<MovieListResponse>> {
        val pager = Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = {
                MovieListPagingSource(category = category)
            }
        )
        return pager.flowable
    }

    override fun fetchMovieReviewPagingData(movieId: String, pageSize: Int): Flowable<PagingData<Review>> {
        val pager = Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = {
                MovieReviewPagingSource(movieId = movieId)
            }
        )
        return pager.flowable
    }

    override fun getMoviePagedListing(category: MovieCategory, pageSize: Int): Listing<MovieModel> {
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

    override fun fetchMovieList(
            category: MovieCategory,
            page: Int?
    ): Single<List<MovieModel>> {
        return movieApi.fetchMovieList(category.key, page).toMovieList()
    }

    override fun fetchMovieDetail(movieId: String): Completable {
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
                            runtime = response.runtime,
                            backdropPath = response.backdropPath
                    )
                }.flatMapCompletable {
                    movieDao.upsert(it)
                    Completable.complete()
                }
    }

    override fun getMovieDetail(movieId: String) = movieDao.queryMovieDetail(movieId)

    override fun fetchMovieVideos(movieId: String): Single<List<Video>> {
        return movieApi.fetchMovieVideos(movieId)
                .map { it.results ?: emptyList() }
    }

    override fun fetchMovieReviews(movieId: String): Single<List<Review>> {
        return movieApi.fetchMovieReviews(movieId)
                .map { it.results ?: emptyList() }
    }

    override fun fetchMovieCasts(movieId: String): Single<List<CastListing.Cast>> {
        return movieApi.fetchMovieCasts(movieId)
                .map { it.castList ?: emptyList() }
    }

    override fun fetchSimilarMovies(movieId: String): Single<List<MovieModel>> {
        return movieApi.fetchSimilarMovies(movieId).toMovieList()
    }

    override fun fetchRecommendationMovies(movieId: String): Single<List<MovieModel>> {
        return movieApi.fetchRecommendationMovies(movieId).toMovieList()
    }

    private fun Single<TmdbApiResponse<MovieListResponse>>.toMovieList(): Single<List<MovieModel>> {
         return this.map {
             it.results?.map { response ->
                 val model = response.toMovieModel()
                 movieDao.upsert(model)
                 model
             }
         }
    }
}