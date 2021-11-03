package com.enginebai.moviehunt.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.enginebai.moviehunt.data.remote.Genre
import com.enginebai.moviehunt.ui.list.MovieCategory
import com.enginebai.moviehunt.utils.DateTimeFormatter
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import com.enginebai.moviehunt.utils.DateTimeFormatter.toCalendarOrNull
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

@TypeConverters
class MovieTypeConverter : KoinComponent {
    private val gson: Gson by inject()

    @TypeConverter
    fun genreListToStr(list: List<Genre>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun strToGenreList(str: String?): List<Genre>? {
        val type = object : TypeToken<List<Genre>>() {}.type
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

    @TypeConverter
    fun calendarToStr(calendar: Calendar?): String? {
        return calendar?.format(DateTimeFormatter.DATE_TIME_FORMAT)
    }

    @TypeConverter
    fun strToCalendar(str: String?): Calendar? {
        return str?.toCalendarOrNull(DateTimeFormatter.DATE_TIME_FORMAT)
    }
}