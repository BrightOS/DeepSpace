package com.example.kotlintraining.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object Instance {
    //private lateinit var DOMAIN : String //= "https://api.nasa.gov/"
    public var builder: Retrofit.Builder? = null
        get() = field

    public fun getInstance(domain : String): Retrofit {
        if (builder == null) {
            val okHttpBuiler: OkHttpClient.Builder = OkHttpClient.Builder()

            builder = Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory( MoshiConverterFactory.create(Moshi.Builder().add(
                    KotlinJsonAdapterFactory()
                ).build()))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpBuiler.build())
        }
        return builder!!.build()
    }
}