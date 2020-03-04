package com.enginebai.moviehunt.ui.movie.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.utils.NetworkState
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener
import com.enginebai.moviehunt.ui.movie.detail.MovieDetailFragment
import com.enginebai.moviehunt.ui.movie.home.MovieCategory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieListFragment : BaseFragment(),
    OnMovieClickListener {

    private val viewModel by sharedViewModel<MovieListViewModel>()
    private val movieCategory: MovieCategory by lazy {
        arguments?.getSerializable(
            FIELD_LIST_CATEGORY
        ) as MovieCategory
    }
    private lateinit var controller: MovieListController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupList()
//        subscribeDataChanges()
        subscribeDataChangesV2()
    }

    private fun setupToolbar() {
        activity?.run {
            buttonBack.setOnClickListener { this.onBackPressed() }
            textCategory.setText(movieCategory.strRes)
        }
    }

    private fun setupList() {
        activity?.let {
            controller = MovieListController(it, this)
        }
        with(listMovie) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setController(controller)
            setItemSpacingRes(R.dimen.padding_small)
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun subscribeDataChanges() {
        viewModel.fetchMovieList(movieCategory)
        subscribePagedList(viewModel.movieList)
        subscribeRefreshState(viewModel.refreshState)
        subscribeNetworkState(viewModel.networkState)
    }

    private fun subscribeDataChangesV2() {
        val listing = viewModel.getList(movieCategory)
        subscribePagedList(listing.pagedList)
        listing.refreshState?.run {
            subscribeRefreshState(this)
        }
        listing.loadMoreState?.run {
            subscribeNetworkState(this)
        }
    }

    private fun subscribePagedList(list: Observable<PagedList<MovieModel>>) {
        list.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                controller.submitList(it)
            }
            .subscribe()
            .disposeOnDestroy()
    }

    private fun subscribeRefreshState(state: Observable<NetworkState>) {
        state.observeOn(AndroidSchedulers.mainThread())
            .doOnNext { swipeRefresh.isRefreshing = (NetworkState.LOADING == it) }
            .subscribe()
            .disposeOnDestroy()
    }

    private fun subscribeNetworkState(state: Observable<NetworkState>) {
        state.observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                controller.loadingMore = (NetworkState.LOADING == it)
                controller.requestModelBuild()
            }
            .subscribe()
            .disposeOnDestroy()
    }

    override fun getLayoutId() = R.layout.fragment_movie_list

    companion object {
        const val FIELD_LIST_CATEGORY = "movieList"

        fun newInstance(listCategory: MovieCategory): MovieListFragment {
            return MovieListFragment().apply {
                arguments = bundleOf(FIELD_LIST_CATEGORY to listCategory)
            }
        }
    }

    override fun onMovieClicked(id: String) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.fragmentContainer, MovieDetailFragment.newInstance(id))
            ?.addToBackStack(MovieDetailFragment::class.java.simpleName)
            ?.commit()
    }
}