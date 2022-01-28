package com.enginebai.moviehunt.ui.reviews

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.remote.Review
import com.enginebai.moviehunt.resources.MHColors
import com.enginebai.moviehunt.resources.MovieHuntTheme
import com.enginebai.moviehunt.ui.widgets.LoadingWidget
import com.enginebai.moviehunt.ui.widgets.MovieReviewWidget
import com.enginebai.moviehunt.ui.widgets.toHolder
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_reviews.*
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieReviewsFragment : BaseFragment() {

    companion object {
        const val FIELD_MOVIE_ID = "movieId"

        fun newInstance(movieId: String): MovieReviewsFragment {
            return MovieReviewsFragment().apply {
                arguments = bundleOf(FIELD_MOVIE_ID to movieId)
            }
        }
    }

    private val viewModel by viewModel<MovieReviewsViewModel>()

    override fun getLayoutId() = R.layout.fragment_movie_reviews

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
                title = getString(R.string.title_reviews)
                setDisplayHomeAsUpEnabled(true)
                show()
            }
        }
    }

    private fun setupList() {
        listReview.setContent {
            MovieHuntTheme {
                MovieReviewList(reviewList = viewModel.fetchReviews(arguments?.getString(FIELD_MOVIE_ID)!!))
            }
        }
    }
}

@Composable
fun MovieReviewList(
    reviewList: Flow<PagingData<Review>>
) {
    val lazyItems = reviewList.collectAsLazyPagingItems()
    LazyColumn {
        if (lazyItems.loadState.refresh is LoadState.Loading) {
            item {
                LoadingWidget(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight())
            }
        }
        items(lazyItems) { review ->
            review?.run {
                Column {
                    Spacer(modifier = Modifier.height(8.dp).background(color = MHColors.background))
                    MovieReviewWidget(
                        avatar = review.author?.getAvatarFullPath(),
                        name = review.author?.username,
                        rating = review.author?.rating,
                        comment = review.content,
                        createdAtDateText = review.createdAt?.format()
                    )
                }
            }
        }
        if (lazyItems.loadState.append is LoadState.Loading) {
            item {
                LoadingWidget(modifier = Modifier.background(MHColors.cardBackground))
            }
        }
    }
}