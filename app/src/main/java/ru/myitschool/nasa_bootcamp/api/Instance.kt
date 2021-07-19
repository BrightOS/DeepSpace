package com.example.kotlintraining.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Instance {
    private const val DOMAIN = "https://api.nasa.gov/"
    public var builder: Retrofit.Builder? = null
    get() = field

    public fun getInstance(): Retrofit {
        if (builder == null) {
            val okHttpBuiler: OkHttpClient.Builder = OkHttpClient.Builder()

            builder = Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpBuiler.build())
        }
        return builder!!.build()
    }
}