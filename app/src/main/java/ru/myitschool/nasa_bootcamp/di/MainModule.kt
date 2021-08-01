package ru.myitschool.nasa_bootcamp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.myitschool.nasa_bootcamp.data.api.NasaApi
import ru.myitschool.nasa_bootcamp.data.api.NewsApi
import ru.myitschool.nasa_bootcamp.data.api.SpaceXApi
import ru.myitschool.nasa_bootcamp.data.api.UpcomingEventsApi
import ru.myitschool.nasa_bootcamp.data.repository.*
import ru.myitschool.nasa_bootcamp.utils.NASA_BASE_URL
import ru.myitschool.nasa_bootcamp.utils.NEWS_BASE_URL
import ru.myitschool.nasa_bootcamp.utils.SPACEX_BASE_URL
import ru.myitschool.nasa_bootcamp.utils.SPACEX_BASE_V5_URL
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {


    @Provides
    @Singleton
    @Named("NASA")
    fun getNasaRetrofit(): Retrofit {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(NASA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    @Named("SPACEX")
    fun getSpaceXRetrofit(): Retrofit {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(SPACEX_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    @Named("NEWS")
    fun getNewsRetrofit(): Retrofit {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    @Named("UPCOMING")
    fun getUpcomingRetrofit(): Retrofit {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(SPACEX_BASE_V5_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }


    @Provides
    @Singleton
    fun getNasaApi(@Named("NASA") retrofit: Retrofit): NasaApi {
        return retrofit.create(NasaApi::class.java)
    }

    @Provides
    @Singleton
    fun getSpaceXApi(@Named("SPACEX") retrofit: Retrofit): SpaceXApi {
        return retrofit.create(SpaceXApi::class.java)
    }

    @Provides
    @Singleton
    fun getUpcomingEventsApi(@Named("UPCOMING") retrofit: Retrofit): UpcomingEventsApi {
        return retrofit.create(UpcomingEventsApi::class.java)
    }


    @Provides
    @Singleton
    fun getNewsApi(@Named("NEWS") retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun getNasaRepository(nasaApi: NasaApi): NasaRepository {
        return NasaRepositoryImpl(nasaApi)
    }

    @Provides
    @Singleton
    fun getSpaceXLaunchRepository(spaceXApi: SpaceXApi): SpaceXRepository {
        return SpaceXRepositoryImpl(spaceXApi)
    }

    @Provides
    @Singleton
    fun getNewsRepository(newsApi: NewsApi): NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }

    @Provides
    @Singleton
    fun getUpcomingRepository(upcomingEventsApi : UpcomingEventsApi): UpcomingRepository {
        return UpcomingRepositoryImpl(upcomingEventsApi)
    }

}