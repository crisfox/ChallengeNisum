package com.nisum.challenge.common.networking

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Configuraciones de retrofit y llamadas http con timeout.
 */
object Client {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private const val TIME_OUT: Long = 120

    val okHttpClient: OkHttpClient = OkHttpClient
        .Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(client)
        .build()

    val provideGson: Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
}
