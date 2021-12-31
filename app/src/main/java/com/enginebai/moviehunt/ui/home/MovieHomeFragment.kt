package com.enginebai.moviehunt.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.MovieDetailFragment
import com.enginebai.moviehunt.ui.home.controller.MovieCarouselController
import com.enginebai.moviehunt.ui.home.controller.MovieHomeController
import com.enginebai.moviehunt.ui.home.controller.MoviePortraitController
import com.enginebai.moviehunt.ui.home.controller.MovieShowcaseController
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.ui.list.MovieListFragment
import com.enginebai.moviehunt.utils.openFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_home.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieHomeFragment : BaseFragment(), MovieClickListener, OnHeaderClickListener {

    private val movieViewModel: MovieHomeViewModel by viewModel()
    private lateinit var homeController: MovieHomeController

    override fun getLayoutId() = R.layout.fragment_movie_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeController = MovieHomeController(view.context, this)
        refreshUpcomingMovieList()
        buildMovieCarouselsForEachCategory()
        buildHomeList()
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refreshUpcomingMovieList() {
        movieViewModel.fetchUpcomingMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { homeController.upcomingMovieList = it }
            .subscribe()
            .disposeOnDestroy()
    }

    private fun buildMovieCarouselsForEachCategory() {
        val categoryListings = mutableMapOf<MovieCategory, MovieCategoryListing>()
        MovieCategory.values()
            .forEachIndexed { index, category ->
                val carouselController: PagingDataEpoxyController<MovieModel>
                val itemsOnScreen: Float
                // first category uses large carousel, other uses normal carousel
                if (index == 0) {
                    carouselController = MovieShowcaseController(category, this)
                    itemsOnScreen = 1.05f
                } else {
                    carouselController = MoviePortraitController(category, this)
                    itemsOnScreen = 3.1f
                }
                categoryListings[category] =
                    MovieCategoryListing(
                        this,
                        LoadState.Loading,
                        carouselController,
                        itemsOnScreen
                    )

                val pagingData = movieViewModel.fetchPagingData(category)
                pagingData
                    .doOnNext {
                        lifecycleScope.launch {
                            carouselController.submitData(it)
                        }
                    }
                    .subscribe()
                    .disposeOnDestroy()
                carouselController.addLoadStateListener {
                    categoryListings[category]?.loadingState = it.refresh
                    swipeRefreshHome.isRefreshing = it.refresh is LoadState.Loading
                    (carouselController as MovieCarouselController).loadingMore =
                        (it.append == LoadState.Loading)
                    homeController.requestModelBuild()
                }
            }
        homeController.categoryListings = categoryListings
    }

    private fun buildHomeList() {
        with(listHome) {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            setControllerAndBuildModels(homeController)
        }
        swipeRefreshHome.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        MovieCategory.values().forEach {
            homeController.categoryListings?.get(it)?.carouselController?.refresh()
        }
        refreshUpcomingMovieList()
    }

    override fun onViewAllClicked(category: MovieCategory) {
        activity?.openFragment(MovieListFragment.newInstance(category), true)
    }

    override fun onMovieClicked(movieId: String) {
        activity?.openFragment(MovieDetailFragment.newInstance(movieId), true)
    }
}