package com.enginebai.moviehunt

import android.app.Application
import com.enginebai.base.di.gsonModule
import com.enginebai.base.di.loggingModule
import com.enginebai.base.di.networkModule
import com.enginebai.moviehunt.di.*
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class AppContext : Application() {

    override fun onCreate() {
        super.onCreate()
        dependenciesInjection()
        initLogging()
    }

    private fun dependenciesInjection() {
        startKoin {
            androidLogger(level = Level.INFO)
            androidContext(this@AppContext)
            modules(
                listOf(
                    gsonModule,
                    networkModule,
                    rxModule,
                    apiModule,
                    dbModule,
                    daoModule,
                    loggingModule,
                    repoModule,
                    viewModelModule
                )
            )
        }
    }

    private fun initLogging() {
        Logger.addLogAdapter(get())
        Timber.plant(get())
    }
}