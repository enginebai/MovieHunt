package com.enginebai.base.di

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
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(get<Interceptor>())
        builder.protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
        builder.build()
    }

    single<Converter.Factory> { GsonConverterFactory.create() }
    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }
    single<Retrofit> {
        // TODO: 5. specify the base URL
        Retrofit.Builder()
            .baseUrl("")
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .client(get())
            .build()
    }
}