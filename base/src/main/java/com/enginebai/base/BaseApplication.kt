package com.enginebai.base

import android.app.Application
import com.enginebai.base.di.errorHandleModule
import com.enginebai.base.di.gsonModule
import com.enginebai.base.di.loggingModule
import com.enginebai.base.di.networkModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import timber.log.Timber

abstract class BaseApplication : Application() {

    abstract fun defineDependencies(): List<Module>

    override fun onCreate() {
        super.onCreate()
        dependenciesInjection()
        initLogging()
    }

    private fun dependenciesInjection() {
        startKoin {
            androidLogger(level = Level.INFO)
            androidContext(this@BaseApplication)
            val dependencies =
                mutableListOf(gsonModule, errorHandleModule, networkModule, loggingModule).apply {
                    addAll(defineDependencies())
                }
            modules(dependencies)
        }
    }

    private fun initLogging() {
        val formatStrategy: FormatStrategy = get()
        val logAdapter: AndroidLogAdapter = get { parametersOf(formatStrategy) }
        Logger.addLogAdapter(logAdapter)
        Timber.plant(get())
    }
}