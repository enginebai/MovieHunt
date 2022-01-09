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
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val DEFAULT_PAGE_SIZE = 5

interface MovieRepo {

    fun fetchMoviePagingData(
        category: MovieCategory,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<MovieListResponse>>

    fun getMoviePagedListing(
        category: MovieCategory,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Listing<MovieModel>

    suspend fun fetchMovieList(
        category: MovieCategory,
        page: Int? = 1
    ): List<MovieModel>?

    fun fetchMovieDetail(movieId: String): Completable
    fun getMovieDetail(movieId: String): Observable<MovieModel>
    fun fetchMovieVideos(movieId: String): Single<List<Video>>
    suspend fun fetchMovieReviews(movieId: String): List<Review>?
    fun fetchMovieReviewPagingData(
        movieId: String,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Review>>

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
    ): Flow<PagingData<MovieListResponse>> {
        val pager = Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = {
                MovieListPagingSource(category = category)
            }
        )
        return pager.flow
    }

    override fun fetchMovieReviewPagingData(
        movieId: String,
        pageSize: Int
    ): Flow<PagingData<Review>> {
        val pager = Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = {
                MovieReviewPagingSource(movieId = movieId)
            }
        )
        return pager.flow
    }

    override fun getMoviePagedListing(category: MovieCategory, pageSize: Int): Listing<MovieModel> {
        val dataSourceFactory = movieDao.queryMovieListDataSource(category)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(pageSize)
            .build()
        // TODO: migrate to paging 3 with RemoteMediator
//        val boundaryCallback = MovieBoundaryCallback(category, pageSize, nextPageIndex)
        val pagedList = RxPagedListBuilder(dataSourceFactory, pagedListConfig)
//            .setBoundaryCallback(boundaryCallback)
            .buildObservable()
        return Listing(
            pagedList = pagedList,
//            refreshState = boundaryCallback.initLoadState,
//            loadMoreState = boundaryCallback.loadMoreState,
//            refresh = {
//                boundaryCallback.onZeroItemsLoaded()
//            }
        )
    }

    override suspend fun fetchMovieList(
        category: MovieCategory,
        page: Int?
    ): List<MovieModel>? {
        return movieApi.fetchMovieList(category.key, page).results?.map { it.toMovieModel() }
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

    override suspend fun fetchMovieReviews(movieId: String): List<Review>? {
        return movieApi.fetchMovieReviews(movieId).results
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