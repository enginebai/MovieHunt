package com.enginebai.moviehunt.ui.home

import androidx.paging.PagingData
import androidx.paging.map
import com.enginebai.base.utils.Listing
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieModelMapper.toMovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Flowable
import org.koin.core.component.inject

class MovieHomeViewModel : BaseViewModel() {

    private val movieRepo: MovieRepo by inject()
    private val listingMap = mutableMapOf<MovieCategory, Flowable<PagingData<MovieModel>>>()

    fun fetchPagingData(category: MovieCategory): Flowable<PagingData<MovieModel>> {
        val listing = movieRepo.fetchMoviePagingData(category)
        listingMap[category] = listing.map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }
        return listingMap[category]!!
    }

    fun fetchUpcomingMovieList() = movieRepo.fetchMovieList(MovieCategory.UPCOMING)

    // TODO: migrate to paging 3 with RemoteMediator
    fun getPagedListing(category: MovieCategory): Listing<MovieModel> {
        return movieRepo.getMoviePagedListing(category)
    }
}