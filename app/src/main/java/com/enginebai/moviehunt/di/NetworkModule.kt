package com.enginebai.moviehunt.di

import coil.ImageLoader
import coil.util.CoilUtils
import coil.util.DebugLogger
import com.enginebai.moviehunt.BuildConfig
import com.enginebai.moviehunt.BuildConfig.API_ROOT
import com.enginebai.moviehunt.utils.ApiInterceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single { ApiInterceptor() }
    single {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(get<HttpLoggingInterceptor>())
        builder.addInterceptor(get<ApiInterceptor>())
        builder.protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
        builder.build()
    }

    single<Converter.Factory> { GsonConverterFactory.create(get()) }
    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }
    single {
        Retrofit.Builder()
            .baseUrl(API_ROOT)
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .client(get())
            .build()
    }

    single {
        ImageLoader.Builder(androidApplication())
            .crossfade(true)
            .logger(if (BuildConfig.DEBUG) DebugLogger() else null)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(androidApplication()))
                    .build()
            }
            .build()
    }
}