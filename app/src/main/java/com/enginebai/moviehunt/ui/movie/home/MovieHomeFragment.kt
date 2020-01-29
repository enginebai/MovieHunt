package com.enginebai.moviehunt.ui.movie.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener
import com.enginebai.moviehunt.ui.movie.list.MovieListFragment
import com.enginebai.moviehunt.ui.movie.list.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieHomeFragment : BaseFragment(), CategoryHeaderHolder.OnHeaderClickListener, OnMovieClickListener {

    private val movieViewModel: MovieListViewModel by viewModel()
    private val movieCategory = arrayOf("now_playing", "popular", "top_rated", "upcoming")

    override fun getLayoutId() = R.layout.fragment_movie_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryListings = mutableListOf<MovieCategoryListing>()
        movieCategory.forEachIndexed { index, category ->
            val carouselController = if (index == 0) {
                MovieLargeListController(this)
            } else {
                MovieNormalListController(this)
            }
            categoryListings.add(MovieCategoryListing(category, this, carouselController))

//            movieViewModel.fetchMovieList(category)
//            movieViewModel.movieList
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext { carouselController.submitList(it) }
//                .subscribe()
//                .disposeOnDestroy()
        }
        val homeController = MovieHomeController().apply {
            this.categoryList = categoryListings
        }
        with (listHome) {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            setController(homeController)
        }
    }

    override fun onMovieClicked(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewAllClicked(category: String) {
        val title = getString(resources.getIdentifier(category, "string", listHome.context.packageName))
        fragmentManager?.beginTransaction()
            ?.add(R.id.fragmentContainer, MovieListFragment.newInstance(title, category))
            ?.commit()
    }
}