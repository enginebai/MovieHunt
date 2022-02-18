package com.enginebai.moviehunt.ui.home

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.LandscapeWidget
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.PortraitWidget
import com.enginebai.moviehunt.data.local.ShowcaseWidget
import com.enginebai.moviehunt.resources.MHDimensions
import com.enginebai.moviehunt.resources.MovieHuntTheme
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.MovieDetailFragment
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.ui.list.MovieListFragment
import com.enginebai.moviehunt.ui.widgets.ListSeparatorWidget
import com.enginebai.moviehunt.ui.widgets.LoadingWidget
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
        composeHome.setContent {
            MovieHuntTheme {
                val upcomingMovieList by movieViewModel.upcomingMovieList.observeAsState()
                val pagingItemsMap = buildMovieCarouselsForEachCategory()

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    pagingItemsMap.forEach { entry ->
                        item {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                TitleWidget(title = stringResource(id = entry.key.strRes),
                                    onClickListener = {
                                        onViewAllClicked(entry.key)
                                    })
                            }
                            // It makes the app ANR here when getting the paging loading state, why?
                            // Timber.wtf("${pagingData.loadState.refresh}")

                            HorizontalMovieList(
                                movieCategory = entry.key,
                                pagingItems = entry.value,
                                movieClickListener = this@MovieHomeFragment
                            )
                        }
                    }

                    if (upcomingMovieList?.isNotEmpty() == true) {
                        item {
                            ListSeparatorWidget()
                            ListSeparatorWidget()
                            TitleWidget(title = stringResource(id = MovieCategory.UPCOMING.strRes))
                        }
                        items(items = upcomingMovieList!!) { movie ->
                            Column {
                                ListSeparatorWidget()
                                movie.LandscapeWidget(onClick = this@MovieHomeFragment::onMovieClicked)
                            }
                        }
                    }
                }
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

    @Composable
    private fun buildMovieCarouselsForEachCategory(): Map<MovieCategory, LazyPagingItems<MovieModel>> {
        val pagingItemsMap = mutableMapOf<MovieCategory, LazyPagingItems<MovieModel>>()
        MovieCategory.values().filterNot { it == MovieCategory.UPCOMING }.forEach { movieCategory ->
            pagingItemsMap[movieCategory] =
                movieViewModel.fetchPagingData(movieCategory).collectAsLazyPagingItems()
        }
        return pagingItemsMap
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
fun HorizontalMovieList(
    movieCategory: MovieCategory,
    pagingItems: LazyPagingItems<MovieModel>,
    movieClickListener: MovieClickListener
) {
    LazyRow(
        // We have to specify the height for nested row regarding performance issue.
        // Source: https://stackoverflow.com/a/70081188/2279285
        modifier = Modifier
            .height(
                if (movieCategory == MovieCategory.NOW_PLAYING) MHDimensions.showcaseHeight.dp
                else MHDimensions.portraitHeight.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding =
        if (movieCategory == MovieCategory.NOW_PLAYING) PaddingValues(horizontal = 8.dp)
        else PaddingValues(start = 8.dp, end = 8.dp, bottom = 12.dp)
    ) {
        if (pagingItems.loadState.refresh is LoadState.Loading) {
            item {
                LoadingWidget(
                    modifier = Modifier
                        .fillParentMaxWidth()
                )
            }
        }
        items(pagingItems) { movie ->
            if (movieCategory == MovieCategory.NOW_PLAYING) {
                movie?.ShowcaseWidget(onClick = movieClickListener::onMovieClicked)
            } else {
                movie?.PortraitWidget(onClick = movieClickListener::onMovieClicked)
            }
        }
        if (pagingItems.loadState.append is LoadState.Loading) {
            item {
                LoadingWidget(
                    modifier = Modifier
                        .fillParentMaxHeight()
                )
            }
        }
    }
    if (movieCategory == MovieCategory.NOW_PLAYING) {
        ListSeparatorWidget()
    }
}