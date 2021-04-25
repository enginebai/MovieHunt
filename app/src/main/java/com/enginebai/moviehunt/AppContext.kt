package com.enginebai.moviehunt

import com.enginebai.base.BaseApplication
import com.enginebai.moviehunt.di.*

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
}