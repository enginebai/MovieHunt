package com.enginebai.moviehunt.di

import androidx.room.Room
import com.enginebai.moviehunt.data.local.MovieDatabase
import com.enginebai.moviehunt.data.remote.MovieApiService
import com.enginebai.moviehunt.data.repo.ConfigRepo
import com.enginebai.moviehunt.data.repo.ConfigRepoImpl
import com.enginebai.moviehunt.data.repo.MovieRepo
import com.enginebai.moviehunt.data.repo.MovieRepoImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(MovieApiService::class.java) }
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MovieDatabase::class.java,
            MovieDatabase::class.java.simpleName
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val daoModule = module {
    single { get<MovieDatabase>().movieDao() }
}

val repoModule = module {
    single<ConfigRepo> { ConfigRepoImpl() }
    single<MovieRepo> { MovieRepoImpl() }
}