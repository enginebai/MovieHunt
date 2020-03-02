package com.enginebai.moviehunt.di

import androidx.room.Room
import com.enginebai.moviehunt.data.local.MovieDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.inMemoryDatabaseBuilder(
            androidApplication(),
            MovieDatabase::class.java
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val daoModule = module {
    single { get<MovieDatabase>().movieDao() }
}