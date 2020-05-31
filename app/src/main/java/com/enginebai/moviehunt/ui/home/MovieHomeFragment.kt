package com.enginebai.moviehunt.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.ui.list.MovieListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieHomeFragment : BaseFragment(), MovieClickListener, CategoryHeaderHolder.OnHeaderClickListener {

	private val movieViewModel: MovieHomeViewModel by viewModel()

	override fun getLayoutId() = R.layout.fragment_movie_home

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		activity?.run {
			(this as AppCompatActivity).supportActionBar?.hide()
		}

		val categoryListings = mutableListOf<MovieCategoryListing>()
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
			categoryListings.add(
				MovieCategoryListing(
					category,
					this,
					carouselController,
					itemsOnScreen
				)
			)

			val listing = movieViewModel.fetchList(category)
			listing.pagedList
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnNext { carouselController.submitList(it) }
				.subscribe()
				.disposeOnDestroy()
			listing.loadMoreState
				?.observeOn(AndroidSchedulers.mainThread())
				?.doOnNext {
					(carouselController as MovieCarouselController).loadingMore = (it == NetworkState.LOADING)
				}?.subscribe()
				?.disposeOnDestroy()
		}

		val homeController = MovieHomeController()
			.apply { this.categoryListings = categoryListings }

		with (listHome) {
			layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
			setController(homeController)
		}
		with (swipeRefreshHome) {
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
		activity?.supportFragmentManager?.beginTransaction()
			?.add(R.id.fragmentContainer, MovieListFragment.newInstance(category))
			?.addToBackStack(MovieListFragment::class.java.simpleName)
			?.commit()
	}

	override fun onMovieClicked(movieId: String) {
		TODO("Not yet implemented")
	}
}