package com.enginebai.moviehunt.ui.movie.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener
import com.enginebai.moviehunt.ui.movie.list.MovieListFragment
import com.enginebai.moviehunt.ui.movie.list.MovieListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieHomeFragment : BaseFragment(), CategoryHeaderHolder.OnHeaderClickListener,
    OnMovieClickListener {

    private val movieViewModel: MovieListViewModel by viewModel()
    private val movieCategory = arrayOf("now_playing", "popular", "top_rated", "upcoming")

    override fun getLayoutId() = R.layout.fragment_movie_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryListings = mutableListOf<MovieCategoryListing>()
        movieCategory.forEachIndexed { index, category ->
            val carouselController: PagedListEpoxyController<MovieModel>
            val itemsOnScreen: Float
            if (index == 0) {
                carouselController = MovieLargeListController(this)
                itemsOnScreen = 1.7f
            } else {
                carouselController = MovieNormalListController(this)
                itemsOnScreen = 3.05f
            }
            categoryListings.add(
                MovieCategoryListing(
                    category,
                    getString(resources.getIdentifier(category, "string", view.context.packageName)),
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
        }
        val homeController = MovieHomeController().apply {
            this.categoryList = categoryListings
        }
        with(listHome) {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            setController(homeController)
        }
    }

    override fun onMovieClicked(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewAllClicked(category: String) {
        fragmentManager?.beginTransaction()
            ?.add(R.id.fragmentContainer, MovieListFragment.newInstance(category))
            ?.addToBackStack(MovieListFragment::class.java.simpleName)
            ?.commit()
    }
}