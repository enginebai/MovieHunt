package com.enginebai.moviehunt.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.enginebai.moviehunt.data.remote.MovieListResponse
import com.enginebai.moviehunt.ui.movie.home.MovieCategory

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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieListId(listId: MovieListId)

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = MovieModel::class)
    fun insertMovieListResponse(response: MovieListResponse): Long

    @Update(onConflict = OnConflictStrategy.REPLACE, entity = MovieModel::class)
    fun updateMovieListResponse(response: MovieListResponse)

    @Transaction
    fun upsertMovieListResponse(response: MovieListResponse) {
        if (insertMovieListResponse(response) == -1L) {
            updateMovieListResponse(response)
        }
    }

    @Transaction
    fun upsertMovieListResponses(category: MovieCategory, list: List<MovieListResponse>) {
        list.forEach {
            upsertMovieListResponse(it)
            insertMovieListId(MovieListId(category = category, movieId = it.id))
        }
    }

    @Query("""
        SELECT movie.*
        FROM movie
        INNER JOIN movie_list ON movie.id == movie_list.movie_id
        WHERE movie_list.category = :category
    """)
    fun queryMovieListDataSource(category: MovieCategory): DataSource.Factory<Int, MovieModel>
}