package com.enginebai.moviehunt.ui.movie.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller =
            MovieListController(this)
        with (listMovie) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setController(controller)
        }

        viewModel.fetchMovieList(arguments?.getString(FIELD_MOVIE_LIST, "") ?: "")
        viewModel.movieList
            .doOnError { it.printStackTrace() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { controller.submitList(it) }
            .subscribe()
            .disposeOnDestroy()
        viewModel.networkState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Timber.d("Network state $it") }
            .subscribe()
            .disposeOnDestroy()
    }

    override fun getLayoutId() = R.layout.fragment_movie_list

    companion object {
        const val FIELD_TITLE = "title"
        const val FIELD_MOVIE_LIST = "movieList"

        fun newInstance(title: String, listCategory: String): MovieListFragment {
            return MovieListFragment().apply {
                arguments = bundleOf(FIELD_TITLE to title, FIELD_MOVIE_LIST to listCategory)
            }
        }
    }

    override fun onMovieClicked(id: String) {
        Timber.d("Click movie $id")
    }
}