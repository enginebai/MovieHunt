package com.enginebai.moviehunt.ui.list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.utils.NetworkState
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.detail.MovieDetailFragment
import com.enginebai.moviehunt.utils.openFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieListFragment : BaseFragment(), MovieClickListener {

    private val viewModelV1 by sharedViewModel<MovieListViewModelV1>()
    private val viewModelV2 by sharedViewModel<MovieListViewModelV2>()
    private val movieCategory: MovieCategory by lazy {
        arguments?.getSerializable(FIELD_LIST_CATEGORY) as MovieCategory
    }
    private lateinit var controller: MovieListController

    override fun getLayoutId() = R.layout.fragment_movie_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupList()
        subscribeDataChangesFromLocal()
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
            controller = MovieListController(it, this)
        }
        with(listMovie) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setController(controller)
            setItemSpacingRes(R.dimen.size_8)
        }
    }

    private fun subscribeDataChangesFromRemoteV1() {
        viewModelV1.fetchMovieList(movieCategory)
        subscribePagedList(viewModelV1.movieList)
        subscribeRefreshState(viewModelV1.refreshState)
        subscribeLoadMoreState(viewModelV1.networkState)
        swipeRefresh.setOnRefreshListener { viewModelV1.refresh() }
    }

    private fun subscribeDataChangesFromRemoteV2() {
        val listing = viewModelV2.fetchList(movieCategory)
        subscribePagedList(listing.pagedList)
        listing.refreshState?.run { subscribeRefreshState(this) }
        listing.loadMoreState?.run { subscribeLoadMoreState(this) }
        swipeRefresh.setOnRefreshListener { listing.refresh() }
    }

    private fun subscribeDataChangesFromLocal() {
        val listing = viewModelV2.getList(movieCategory)
        subscribePagedList(listing.pagedList)
        listing.refreshState?.run { subscribeRefreshState(this) }
        listing.loadMoreState?.run { subscribeLoadMoreState(this) }
        swipeRefresh.setOnRefreshListener { listing.refresh() }
    }

    private fun subscribePagedList(list: Observable<PagedList<MovieModel>>) {
        list.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { controller.submitList(it) }
            .subscribe()
            .disposeOnDestroy()
    }

    private fun subscribeRefreshState(state: Observable<NetworkState>) {
        state.observeOn(AndroidSchedulers.mainThread())
            .doOnNext { swipeRefresh.isRefreshing = (NetworkState.LOADING == it) }
            .subscribe()
            .disposeOnDestroy()
    }

    private fun subscribeLoadMoreState(state: Observable<NetworkState>) {
        state.observeOn(AndroidSchedulers.mainThread())
            .doOnNext { controller.loadingMore = (NetworkState.LOADING == it) }
            .subscribe()
            .disposeOnDestroy()
    }

    companion object {
        const val FIELD_LIST_CATEGORY = "movieListCategory"

        fun newInstance(category: MovieCategory): MovieListFragment = MovieListFragment().apply {
            arguments = bundleOf(FIELD_LIST_CATEGORY to category)
        }
    }
}