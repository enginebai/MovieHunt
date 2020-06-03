package com.enginebai.moviehunt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieModel::class], version = 1, exportSchema = true)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase: RoomDatabase() {
	abstract fun movieDao(): MovieDao
}