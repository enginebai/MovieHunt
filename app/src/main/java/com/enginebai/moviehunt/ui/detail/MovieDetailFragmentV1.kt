package com.enginebai.moviehunt.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.enginebai.base.view.BaseBindingFragment
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.databinding.FragmentMovieDetailV1Binding
import kotlinx.android.synthetic.main.fragment_movie_detail_v1.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Deprecated("Use v2")
class MovieDetailFragmentV1 : BaseBindingFragment<FragmentMovieDetailV1Binding>() {

    private val movieViewModel: MovieDetailViewModelV1 by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = movieViewModel
        movieViewModel.fetchMovieDetail(arguments?.getString(FIELD_MOVIE_ID) ?: "")
        buttonBack.setOnClickListener { activity?.onBackPressed() }
    }

    override fun getLayoutId() = R.layout.fragment_movie_detail_v1

    companion object {
        const val FIELD_MOVIE_ID = "movieId"

        fun newInstance(movieId: String): MovieDetailFragmentV1 {
            return MovieDetailFragmentV1().apply {
                arguments = bundleOf(FIELD_MOVIE_ID to movieId)
            }
        }
    }
}