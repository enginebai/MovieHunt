package com.enginebai.base.di

import com.enginebai.base.BuildConfig
import com.enginebai.base.utils.ApiInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }
    single {
        ApiInterceptor()
    }
    single {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(get<Interceptor>())
        builder.addInterceptor(get<ApiInterceptor>())
        builder.protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
        builder.build()
    }

    single<Converter.Factory> { GsonConverterFactory.create() }
    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_ROOT)
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .client(get())
            .build()
    }
}