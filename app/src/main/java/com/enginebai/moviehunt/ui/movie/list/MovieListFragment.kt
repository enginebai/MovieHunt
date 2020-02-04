package com.enginebai.moviehunt.ui.movie.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enginebai.base.utils.NetworkState
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MovieListFragment : BaseFragment(),
    OnMovieClickListener {

    private val viewModel by sharedViewModel<MovieListViewModel>()
    private lateinit var controller: MovieListController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupList()
        subscribeDataChanges()
    }

    private fun setupToolbar() {
        activity?.run {
            buttonBack.setOnClickListener { this.onBackPressed() }
            Timber.d("${arguments?.getString(FIELD_LIST_CATEGORY)}")
            textCategory.text = getString(
                resources.getIdentifier(
                    arguments?.getString(
                        FIELD_LIST_CATEGORY
                    ), "string", this.packageName
                )
            )
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
        viewModel.fetchMovieList(arguments?.getString(FIELD_LIST_CATEGORY, "") ?: "")
        viewModel.movieList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                controller.submitList(it)
            }
            .subscribe()
            .disposeOnDestroy()
        viewModel.refreshState
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { swipeRefresh.isRefreshing = (NetworkState.LOADING == it) }
            .subscribe()
            .disposeOnDestroy()
        viewModel.networkState
            .observeOn(AndroidSchedulers.mainThread())
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

        fun newInstance(listCategory: String): MovieListFragment {
            return MovieListFragment().apply {
                arguments = bundleOf(FIELD_LIST_CATEGORY to listCategory)
            }
        }
    }

    override fun onMovieClicked(id: String) {
        Timber.d("Click movie $id")
    }
}