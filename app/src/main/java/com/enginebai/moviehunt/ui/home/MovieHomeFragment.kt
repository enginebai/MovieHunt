package com.enginebai.moviehunt.ui.home

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Observer
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.LandscapeWidget
import com.enginebai.moviehunt.data.local.PortraitWidget
import com.enginebai.moviehunt.data.local.ShowcaseWidget
import com.enginebai.moviehunt.data.local.toPortraitHolder
import com.enginebai.moviehunt.resources.MovieHuntTheme
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.MovieDetailFragment
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.ui.list.MovieListFragment
import com.enginebai.moviehunt.ui.widgets.ListSeparatorWidget
import com.enginebai.moviehunt.ui.widgets.TitleWidget
import com.enginebai.moviehunt.utils.openFragment
import kotlinx.android.synthetic.main.fragment_movie_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieHomeFragment : BaseFragment(), MovieClickListener, OnHeaderClickListener {

    private val movieViewModel: MovieHomeViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_movie_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshUpcomingMovieList()
        buildMovieCarouselsForEachCategory()
        composeHome.setContent {
            MovieHuntTheme {
                MovieHomeWidget(viewModel = movieViewModel, movieClickListener = this)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refreshUpcomingMovieList() {
        movieViewModel.fetchUpcomingMovieList()
    }

    private fun buildMovieCarouselsForEachCategory() {
        MovieCategory.values()
            .forEachIndexed { index, category ->
                // TODO: build the horizontal list
            }
    }

    private fun refresh() {
        MovieCategory.values().forEach {
            // TODO: refresh each category
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

@Composable
fun MovieHomeWidget(viewModel: MovieHomeViewModel, movieClickListener: MovieClickListener) {
    val upcomingMovieList by viewModel.upcomingMovieList.observeAsState()

    LazyColumn(modifier = Modifier.fillMaxHeight()) {

        MovieCategory.values().forEach { movieCategory ->
            item {
                TitleWidget(title = stringResource(id = movieCategory.strRes))
            }

            item {
                val pagingData =
                    viewModel.fetchPagingData(movieCategory).collectAsLazyPagingItems()
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(pagingData) { movie ->
                        if (movieCategory == MovieCategory.NOW_PLAYING) {
                            movie?.ShowcaseWidget(onClick = movieClickListener::onMovieClicked)
                        } else {
                            movie?.PortraitWidget(onClick = movieClickListener::onMovieClicked)
                        }
                    }
                }
            }
        }

        if (upcomingMovieList?.isNotEmpty() == true) {
            item {
                TitleWidget(title = stringResource(id = MovieCategory.UPCOMING.strRes))
            }
            items(upcomingMovieList!!) { movie ->
                Column {
                    ListSeparatorWidget()
                    movie.LandscapeWidget(onClick = movieClickListener::onMovieClicked)
                }
            }
        }
    }
}