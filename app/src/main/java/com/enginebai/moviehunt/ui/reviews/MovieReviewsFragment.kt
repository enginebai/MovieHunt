package com.enginebai.moviehunt.ui.reviews

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.remote.Review
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
    private val controller by lazy {
        MovieReviewsController()
    }

    override fun getLayoutId() = R.layout.fragment_movie_reviews

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textTitle.text = getString(R.string.title_reviews)
        buttonBack.setOnClickListener { activity?.onBackPressed() }

        listReviews.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        listReviews.setController(controller)
        listReviews.setItemSpacingRes(R.dimen.size_20)

        lifecycleScope.launch {
            viewModel.fetchReviews(arguments?.getString(FIELD_MOVIE_ID)!!).collectLatest {
                controller.submitData(it)
            }
        }
    }
}