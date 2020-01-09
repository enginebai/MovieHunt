package com.enginebai.project

import android.app.Application
import com.enginebai.project.di.loggingModule
import com.enginebai.project.di.networkModule
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
            modules(listOf(networkModule, loggingModule))
        }
    }

    private fun initLogging() {
        Logger.addLogAdapter(get())
        Timber.plant(get())
    }
}