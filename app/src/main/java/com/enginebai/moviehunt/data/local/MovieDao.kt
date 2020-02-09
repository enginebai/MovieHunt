package com.enginebai.moviehunt.data.local

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: MovieModel): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(movie: MovieModel)

    @Transaction
    fun upsert(movie: MovieModel) {
        if (insert(movie) == -1L) {
            update(movie)
        }
    }

    @Transaction
    fun upsertList(list: List<MovieModel>) {
        list.forEach {
            upsert(it)
        }
    }

    @Query("SELECT")
    fun queryMovieListObservable(): DataSource.Factory<Int, MovieModel>
}