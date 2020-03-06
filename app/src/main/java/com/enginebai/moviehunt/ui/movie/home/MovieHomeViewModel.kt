package com.enginebai.moviehunt.ui.movie.home

import com.enginebai.base.utils.Listing
import com.enginebai.base.utils.NetworkState
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.repo.MovieRepo
import io.reactivex.Observable
import org.koin.core.inject

class MovieHomeViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()

    private val listingMap = mutableMapOf<MovieCategory, Listing<MovieModel>>()

    fun fetchList(category: MovieCategory): Listing<MovieModel> {
        val listing = movieRepo.fetchMovieList(category)
        listingMap[category] = listing
        return listing
    }

    fun getList(category: MovieCategory): Listing<MovieModel> {
        val listing = movieRepo.getMovieList(category)
        listingMap[category] = listing
        return listing
    }

    fun refresh() {
        listingMap.values.forEach {
            it.refresh.invoke()
        }
    }

    fun refreshState(): Observable<NetworkState> {
        val stateList = mutableListOf<Observable<NetworkState>>()
        listingMap.values.forEach {
            it.refreshState?.apply {
                stateList.add(this)
            }
        }
        return Observable.combineLatest(stateList) { states ->
            var mergeState = NetworkState.IDLE
            states.forEach {
                val s = it as NetworkState
                if (s == NetworkState.LOADING) {
                    mergeState = NetworkState.LOADING
                }
            }
            mergeState
        }
    }
}