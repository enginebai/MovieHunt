package com.enginebai.moviehunt.ui.list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.LandscapeWidget
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.resources.MHColors
import com.enginebai.moviehunt.resources.MovieHuntTheme
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.MovieDetailFragment
import com.enginebai.moviehunt.ui.widgets.LoadingWidget
import com.enginebai.moviehunt.utils.openFragment
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieListFragment : BaseFragment(), MovieClickListener {

    private val viewModel by sharedViewModel<MovieListViewModel>()
    private val movieCategory: MovieCategory by lazy {
        arguments?.getSerializable(FIELD_LIST_CATEGORY) as MovieCategory
    }

    override fun getLayoutId() = R.layout.fragment_movie_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        // make fragment.onOptionsItemSelected() be called
        // https://stackoverflow.com/a/37953823/2279285
        setHasOptionsMenu(true)
        activity?.run {
            (this as AppCompatActivity).setSupportActionBar(toolbar)
            this.supportActionBar?.run {
                setTitle(movieCategory.strRes)
                setDisplayHomeAsUpEnabled(true)
                show()
            }
        }
    }

    private fun setupList() {
        listMovie.setContent {
            MovieHuntTheme {
                MovieListWidget(
                    movies = viewModel.fetchPagingData(movieCategory),
                    clickListener = this,
                )
            }
        }
    }

    override fun onMovieClicked(movieId: String) {
        activity?.openFragment(MovieDetailFragment.newInstance(movieId), true)
    }

    companion object {
        const val FIELD_LIST_CATEGORY = "movieListCategory"

        fun newInstance(category: MovieCategory): MovieListFragment = MovieListFragment().apply {
            arguments = bundleOf(FIELD_LIST_CATEGORY to category)
        }
    }
}

@Composable
fun MovieListWidget(
    movies: Flow<PagingData<MovieModel>>,
    clickListener: MovieClickListener
) {
    val lazyMovieItems = movies.collectAsLazyPagingItems()

    SwipeRefresh(
        state = rememberSwipeRefreshState(
            isRefreshing = lazyMovieItems.loadState.refresh == LoadState.Loading
        ),
        onRefresh = { lazyMovieItems.refresh() },
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state, refreshTriggerDistance = refreshTrigger,
                backgroundColor = Color.White
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (lazyMovieItems.loadState.refresh is LoadState.Loading) {
                item {
                    LoadingWidget(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .fillParentMaxHeight()
                    )
                }
            }

            items(lazyMovieItems) { movie ->
                movie?.run {
                    movie.LandscapeWidget(onClick = { clickListener.onMovieClicked(movie.id) })
                }
            }

            if (lazyMovieItems.loadState.append is LoadState.Loading) {
                item {
                    LoadingWidget(modifier = Modifier.background(MHColors.cardBackground))
                }
            }
        }
    }
}