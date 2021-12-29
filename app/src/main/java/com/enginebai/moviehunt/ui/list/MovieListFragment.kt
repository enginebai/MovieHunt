package com.enginebai.moviehunt.ui.list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.MovieDetailFragment
import com.enginebai.moviehunt.utils.openFragment
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.launch
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
        subscribePagingDataFromRemote()
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
        with(listMovie) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setController(controller)
            setItemSpacingRes(R.dimen.size_8)
        }
        swipeRefresh.setOnRefreshListener {
            controller.refresh()
        }
    }

    private fun subscribePagingDataFromRemote() {
        val pagingData = viewModel.fetchPagingData(movieCategory)
        pagingData.doOnNext {
            // TODO: double check if this coroutine runs correctly.
            lifecycleScope.launch {
                controller.submitData(it)
            }
        }.subscribe()
            .disposeOnDestroy()

        controller.addLoadStateListener {
            Timber.d("Source.append=${it.source.append}\nSource.refresh=${it.source.refresh}\nAppend=${it.append}\nRefresh=${it.refresh}")
            swipeRefresh.isRefreshing = (it.refresh == LoadState.Loading)
            controller.loadingMore = (it.append == LoadState.Loading)
        }
    }

    companion object {
        const val FIELD_LIST_CATEGORY = "movieListCategory"

        fun newInstance(category: MovieCategory): MovieListFragment = MovieListFragment().apply {
            arguments = bundleOf(FIELD_LIST_CATEGORY to category)
        }
    }
}