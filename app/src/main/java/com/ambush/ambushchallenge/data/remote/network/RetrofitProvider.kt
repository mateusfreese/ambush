package com.ambush.ambushchallenge.data.remote.network

import com.ambush.ambushchallenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitProvider {
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.takeIf { BuildConfig.DEBUG }?.apply {
            addInterceptor(interceptor)
        }

        return httpClientBuilder.build()
    }
}

inline fun <reified T> createWebService(): T {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(RetrofitProvider().providesOkHttpClient())
        .build()
        .create(T::class.java)
}
