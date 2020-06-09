package com.enginebai.moviehunt.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.enginebai.moviehunt.data.remote.Genre
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.core.KoinComponent
import org.koin.core.inject

@TypeConverters
class MovieTypeConverter : KoinComponent {
	private val gson: Gson by inject()

	@TypeConverter
	fun genreListToStr(list: List<Genre>?): String? {
		return gson.toJson(list)
	}

	@TypeConverter
	fun strToGenreList(str: String?): List<Genre>? {
		val type = object : TypeToken<List<Genre>>(){}.type
		return gson.fromJson(str, type)
	}

	@TypeConverter
	fun movieCategoryToStr(category: MovieCategory?): String? {
		return gson.toJson(category)
	}

	@TypeConverter
	fun strToMovieCategory(str: String?): MovieCategory? {
		return gson.fromJson(str, MovieCategory::class.java)
	}
}