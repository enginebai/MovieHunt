package com.enginebai.moviehunt

import coil.Coil
import coil.ImageLoader
import com.enginebai.base.BaseApplication
import com.enginebai.moviehunt.di.*
import org.koin.android.ext.android.get

class AppContext : BaseApplication() {

    override fun defineDependencies() = listOf(
        loggingModule,
        gsonModule,
        errorHandleModule,
        networkModule,
        appModule,
        viewModelModule,
        apiModule,
        dbModule,
        daoModule,
        repoModule
    )

    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader(get<ImageLoader>())
    }
}