package com.enginebai.moviehunt

import com.enginebai.base.view.BaseActivity

import android.os.Bundle
import com.enginebai.moviehunt.data.MovieApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService: MovieApiService = get()
        apiService.fetchMovieDetail("181812")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {

            }
            .subscribe()
    }

    override fun getLayoutId() = R.layout.activity_main
}
