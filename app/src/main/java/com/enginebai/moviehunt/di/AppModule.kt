package com.enginebai.moviehunt.di

import com.enginebai.moviehunt.utils.RxErrorHandler
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val rxModule = module {
    single(createdAtStart = true) { RxErrorHandler(androidApplication()) }
}