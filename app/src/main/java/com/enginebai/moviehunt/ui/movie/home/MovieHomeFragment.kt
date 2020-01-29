package com.enginebai.moviehunt.ui.movie.home

import android.os.Bundle
import android.view.View
import com.enginebai.base.view.BaseFragment
import com.enginebai.moviehunt.R

val movieCategory = arrayOf("now_playing", "popular", "top_rated", "upcoming")

class MovieHomeFragment : BaseFragment() {
    override fun getLayoutId() = R.layout.fragment_movie_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}