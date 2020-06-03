package com.enginebai.moviehunt.data.local

import androidx.room.*
import com.enginebai.moviehunt.data.remote.MovieListResponse
import com.enginebai.moviehunt.ui.list.MovieCategory

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
	fun upsertMovieListResponse(category: MovieCategory, list: List<MovieListResponse>) {
		list.forEach {
			upsertMovieListResponse(it)
			insertMovieListId(MovieListId(category = category, movieId = it.id))
		}
	}



}