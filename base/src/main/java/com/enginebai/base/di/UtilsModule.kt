package com.enginebai.base.di

import com.enginebai.base.BuildConfig
import com.enginebai.base.utils.logging.MyCustomLoggingDebugTree
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