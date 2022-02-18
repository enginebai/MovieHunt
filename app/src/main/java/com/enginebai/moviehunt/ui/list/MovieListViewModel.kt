package com.enginebai.moviehunt.ui.list

import androidx.paging.PagingData
import androidx.paging.map
import com.enginebai.base.utils.Listing
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieModelMapper.toMovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.inject

class MovieListViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    fun fetchPagingData(category: MovieCategory): Flow<PagingData<MovieModel>> {
        return movieRepo.fetchMoviePagingData(category).map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }
    }

    // TODO: migrate to paging 3 with RemoteMediator
//    fun getPagedListing(category: MovieCategory): Listing<MovieModel> {
//        return movieRepo.getMoviePagedListing(category)
//    }
}