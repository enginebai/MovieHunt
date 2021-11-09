package com.enginebai.moviehunt.di

import com.enginebai.base.utils.logging.TimberLoggerDebugTree
import com.enginebai.moviehunt.BuildConfig
import com.enginebai.moviehunt.utils.CalendarDeserializer
import com.enginebai.moviehunt.utils.ExceptionHandler
import com.google.gson.GsonBuilder
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import timber.log.Timber
import java.util.*

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
    single { CalendarDeserializer }
    single {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Calendar::class.java, get<CalendarDeserializer>())
        gsonBuilder.create()
    }
}

val errorHandleModule = module {
    single(createdAtStart = true) { ExceptionHandler(androidApplication()) }
}