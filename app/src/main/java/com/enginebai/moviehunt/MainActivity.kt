package com.enginebai.moviehunt

import android.os.Bundle
import androidx.annotation.StringRes
import com.enginebai.base.view.BaseActivity
import com.enginebai.moviehunt.ui.movie.list.MovieListFragment

data class MovieListCategory(@StringRes val name: Int, val apiPath: String)

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, MovieListFragment.newInstance(getString(R.string.now_playing), "now_playing"))
            .commit()
    }

    override fun getLayoutId() = R.layout.activity_main
}
