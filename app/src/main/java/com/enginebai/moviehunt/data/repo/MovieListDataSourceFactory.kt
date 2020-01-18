package com.enginebai.moviehunt.data.repo

import androidx.paging.DataSource
import com.enginebai.moviehunt.data.local.MovieModel

class MovieListDataSourceFactory(private val dataSource: MovieListDataSource) :
    DataSource.Factory<Int, MovieModel>() {

    override fun create(): DataSource<Int, MovieModel> {
        return dataSource
    }
}