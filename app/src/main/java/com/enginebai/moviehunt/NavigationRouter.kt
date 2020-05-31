package com.enginebai.moviehunt

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.enginebai.moviehunt.ui.home.MovieHomeFragment
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.ui.list.MovieListFragment
import com.enginebai.moviehunt.ui.movie.detail.MovieDetailFragment

object NavigationRouter {
	fun navigateToHome(activity: FragmentActivity?) {
		activity?.supportFragmentManager?.beginTransaction()
			?.add(R.id.fragmentContainer, MovieHomeFragment())
			?.commit()
	}

	fun navigationToList(activity: FragmentActivity?, movieCategory: MovieCategory) {
		activity?.supportFragmentManager?.beginTransaction()
			?.add(R.id.fragmentContainer, MovieListFragment.newInstance(movieCategory))
			?.addToBackStack(MovieListFragment::class.java.simpleName)
			?.commit()
	}

	fun navigationToDetail(activity: FragmentActivity?, movieId: String) {
		activity?.supportFragmentManager?.beginTransaction()
			?.add(R.id.fragmentContainer, MovieDetailFragment.newInstance(movieId))
			?.addToBackStack(MovieListFragment::class.java.simpleName)
			?.commit()
	}
}