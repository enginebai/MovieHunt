package com.enginebai.moviehunt.ui.list

import androidx.paging.PagingData
import androidx.paging.map
import com.enginebai.base.utils.Listing
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieListResponse
import com.enginebai.moviehunt.data.remote.MovieModelMapper.toMovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import io.reactivex.Flowable
import org.koin.core.component.inject

class MovieListViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    fun fetchPagedListing(category: MovieCategory): Flowable<PagingData<MovieModel>> {
        return movieRepo.fetchMoviePagedListing(category).map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }
    }

    fun getPagedListing(category: MovieCategory): Listing<MovieModel> {
        return movieRepo.getMoviePagedListing(category)
    }
}