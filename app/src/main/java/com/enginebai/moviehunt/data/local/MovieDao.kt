package com.enginebai.moviehunt.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.enginebai.moviehunt.data.remote.MovieListResponse
import com.enginebai.moviehunt.ui.list.MovieCategory
import io.reactivex.Observable

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: MovieModel): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(movie: MovieModel)

    @Transaction
    fun upsert(movie: MovieModel) {
        if (insert(movie) == -1L)
            update(movie)
    }

    @Query("SELECT movie.* FROM `movie` WHERE `id` = :movieId")
    fun queryMovieDetail(movieId: String): Observable<MovieModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = MovieModel::class)
    fun insertMovieListResponse(response: MovieListResponse): Long

    @Update(onConflict = OnConflictStrategy.REPLACE, entity = MovieModel::class)
    fun updateMovieListResponse(response: MovieListResponse)

    @Transaction
    fun upsertMovieListResponse(response: MovieListResponse) {
        if (insertMovieListResponse(response) == -1L)
            updateMovieListResponse(response)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieListId(listId: MovieListId)

    @Transaction
    fun upsertMovieListResponse(
        category: MovieCategory,
        list: List<MovieListResponse>,
        pageSize: Int,
        currentPage: Int
    ) {
        // 使用 Page size x current page + list index 來當資料位置
        list.forEachIndexed { index, movieListResponse ->
            upsertMovieListResponse(movieListResponse)
            insertMovieListId(
                MovieListId(
                    category = category, movieId = movieListResponse.id,
                    position = (if (currentPage <= 0) 0 else (currentPage - 1)).times(pageSize)
                        .plus(index).toLong()
                )
            )
        }
    }

    @Query(
        """
		SELECT movie.*
		FROM movie
		INNER JOIN movie_list ON movie.id == movie_list.movie_id
		WHERE movie_list.category = :category
	"""
    )
    fun queryMovieListDataSource(category: MovieCategory): DataSource.Factory<Int, MovieModel>

    @Query("DELETE FROM movie_list WHERE category = :category")
    fun deleteMovieList(category: MovieCategory)
}