package com.enginebai.project.di

import com.enginebai.project.BuildConfig
import com.enginebai.project.utils.logging.MyCustomLoggingDebugTree
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogAdapter
import org.koin.dsl.module
import timber.log.Timber

val loggingModule = module {
    single<LogAdapter> {
        object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        }
    }

    single<Timber.Tree> { MyCustomLoggingDebugTree() }
}