package com.enginebai.moviehunt.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.enginebai.base.utils.Listing
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.remote.MovieModelMapper.toMovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import com.enginebai.moviehunt.ui.list.MovieCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class MovieHomeViewModel : BaseViewModel() {

    private val movieRepo: MovieRepo by inject()
    private val _upcomingMovieList = MutableLiveData<List<MovieModel>?>()
    val upcomingMovieList: LiveData<List<MovieModel>?> = _upcomingMovieList

    fun fetchPagingData(category: MovieCategory): Flow<PagingData<MovieModel>> {
        val listing = movieRepo.fetchMoviePagingData(category)
        return listing.map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.cachedIn(viewModelScope)
    }

    fun fetchUpcomingMovieList() {
        viewModelScope.launch {
            _upcomingMovieList.value = movieRepo.fetchMovieList(MovieCategory.UPCOMING)
        }
    }

    // TODO: migrate to paging 3 with RemoteMediator
//    fun getPagedListing(category: MovieCategory): Listing<MovieModel> {
//        return movieRepo.getMoviePagedListing(category)
//    }
}