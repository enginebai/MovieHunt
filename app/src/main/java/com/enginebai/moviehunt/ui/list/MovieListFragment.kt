package com.enginebai.moviehunt.ui.list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.display5StarsRating
import com.enginebai.moviehunt.data.local.displayTitle
import com.enginebai.moviehunt.data.local.displayVoteCount
import com.enginebai.moviehunt.data.remote.ImageApi
import com.enginebai.moviehunt.data.remote.ImageSize
import com.enginebai.moviehunt.resources.MHColors
import com.enginebai.moviehunt.resources.MovieHuntTheme
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.MovieDetailFragment
import com.enginebai.moviehunt.ui.widgets.LoadingWidget
import com.enginebai.moviehunt.ui.widgets.MovieLandscapeWidget
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import com.enginebai.moviehunt.utils.openFragment
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MovieListFragment : BaseFragment(), MovieClickListener {

    private val viewModel by sharedViewModel<MovieListViewModel>()
    private val movieCategory: MovieCategory by lazy {
        arguments?.getSerializable(FIELD_LIST_CATEGORY) as MovieCategory
    }
    private lateinit var controller: MovieLandscapeController

    override fun getLayoutId() = R.layout.fragment_movie_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupList()
    }

    override fun onMovieClicked(movieId: String) {
        activity?.openFragment(MovieDetailFragment.newInstance(movieId), true)
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
        activity?.let {
            controller = MovieLandscapeController(this)
        }
        swipeRefresh.setOnRefreshListener {
            controller.refresh()
        }

        listMovie.setContent {
            MovieHuntTheme {
                MovieListWidget(
                    movies = viewModel.fetchPagingData(movieCategory),
                    clickListener = this
                )
            }
        }
    }

    companion object {
        const val FIELD_LIST_CATEGORY = "movieListCategory"

        fun newInstance(category: MovieCategory): MovieListFragment = MovieListFragment().apply {
            arguments = bundleOf(FIELD_LIST_CATEGORY to category)
        }
    }
}

@Composable
fun MovieListWidget(movies: Flow<PagingData<MovieModel>>, clickListener: MovieClickListener) {
    val lazyMovieItems = movies.collectAsLazyPagingItems()

    LazyColumn {
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
                MovieLandscapeWidget(
                    movieId = movie.id,
                    imagePoster = ImageApi.getFullUrl(this.posterPath, ImageSize.W500),
                    textTitle = this.displayTitle(),
                    rating = this.display5StarsRating(),
                    ratingTotalCountText = this.displayVoteCount(),
                    genre = this.genreList?.map { it.name }?.joinToString(),
                    releaseDateText = this.releaseDate?.format(),
                    itemClickListener = { clickListener.onMovieClicked(this.id) }
                )
            }
        }

        if (lazyMovieItems.loadState.append is LoadState.Loading) {
            item {
                LoadingWidget(modifier = Modifier.background(MHColors.cardBackground))
            }
        }

        lazyMovieItems.apply {
            Timber.d("Source.append=${loadState.source.append}\nSource.refresh=${loadState.source.refresh}\nAppend=${loadState.append}\nRefresh=${loadState.refresh}")
        }
    }
}