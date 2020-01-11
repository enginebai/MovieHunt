package com.enginebai.moviehunt.data.repo

import androidx.paging.DataSource
import com.enginebai.moviehunt.data.local.MovieModel
import io.reactivex.subjects.BehaviorSubject

class MovieListDataSourceFactory(private val movieList: String) :
    DataSource.Factory<Int, MovieModel>() {

    val dataSource = BehaviorSubject.create<MovieListDataSource>()

    override fun create(): DataSource<Int, MovieModel> {
        return MovieListDataSource(movieList).apply {
            dataSource.onNext(this)
        }
    }
}