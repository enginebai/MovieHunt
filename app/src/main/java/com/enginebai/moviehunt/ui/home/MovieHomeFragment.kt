package com.enginebai.moviehunt.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.base.utils.NetworkState
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.home.controller.MovieCarouselController
import com.enginebai.moviehunt.ui.home.controller.MovieHomeController
import com.enginebai.moviehunt.ui.home.controller.MovieLargeListController
import com.enginebai.moviehunt.ui.home.controller.MovieNormalListController
import com.enginebai.moviehunt.ui.home.models.CategoryHeaderHolder
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.ui.list.MovieListFragment
import com.enginebai.moviehunt.ui.detail.MovieDetailFragment
import com.enginebai.moviehunt.utils.openFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieHomeFragment : BaseFragment(), MovieClickListener,
    CategoryHeaderHolder.OnHeaderClickListener {

    private val movieViewModel: MovieHomeViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_movie_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryListings = mutableMapOf<MovieCategory, MovieCategoryListing>()
        val homeController = MovieHomeController()
        MovieCategory.values().forEachIndexed { index, category ->
            val carouselController: PagedListEpoxyController<MovieModel>
            val itemsOnScreen: Float
            // first category uses large carousel, other uses normal carousel
            if (index == 0) {
                carouselController = MovieLargeListController(category, this)
                itemsOnScreen = 1.7f
            } else {
                carouselController = MovieNormalListController(category, this)
                itemsOnScreen = 3.05f
            }
            categoryListings[category] =
                MovieCategoryListing(
                    this,
                    NetworkState.LOADING,
                    carouselController,
                    itemsOnScreen
                )

            val listing = movieViewModel.getList(category)
            listing.pagedList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { carouselController.submitList(it) }
                .subscribe()
                .disposeOnDestroy()
            listing.refreshState
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnNext {
                    categoryListings[category]?.loadingState = it
                    homeController.requestModelBuild()
                }?.subscribe()
                ?.disposeOnDestroy()
            listing.loadMoreState
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnNext {
                    (carouselController as MovieCarouselController).loadingMore =
                        (it == NetworkState.LOADING)
                }?.subscribe()
                ?.disposeOnDestroy()
        }
        homeController.categoryListings = categoryListings

        with(listHome) {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            setControllerAndBuildModels(homeController)
        }
        with(swipeRefreshHome) {
            movieViewModel.refreshState()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { this.isRefreshing = (it == NetworkState.LOADING) }
                .subscribe()
                .disposeOnDestroy()

            setOnRefreshListener {
                movieViewModel.refresh()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        movieViewModel.refresh()
    }

    override fun onViewAllClicked(category: MovieCategory) {
        activity?.openFragment(MovieListFragment.newInstance(category), true)
    }

    override fun onMovieClicked(movieId: String) {
        activity?.openFragment(MovieDetailFragment.newInstance(movieId), true)
    }
}