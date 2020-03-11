package com.enginebai.base.di

import com.enginebai.base.BuildConfig
import com.enginebai.base.utils.RxErrorHandler
import com.enginebai.base.utils.logging.TimberLoggerDebugTree
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import timber.log.Timber

val loggingModule = module {
    single<AndroidLogAdapter> { (formatStrategy: FormatStrategy) ->
        object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        }
    }

    single<FormatStrategy> {
        PrettyFormatStrategy.newBuilder()
            .tag("enginebai")
            .methodCount(3)
            .methodOffset(5) // avoid timber internal stack track
            .build()
    }

    single<Timber.Tree> { TimberLoggerDebugTree() }
}

val gsonModule = module {
    single { Gson() }
}

val errorHandleModule = module {
    single(createdAtStart = true) { RxErrorHandler(androidApplication()) }
}