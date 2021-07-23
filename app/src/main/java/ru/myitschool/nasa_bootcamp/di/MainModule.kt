package ru.myitschool.nasa_bootcamp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.myitschool.nasa_bootcamp.data.repository.ImageOfDayRepository
import ru.myitschool.nasa_bootcamp.data.repository.ImageOfDayRepositoryImpl
import ru.myitschool.nasa_bootcamp.data.api.NasaApi
import ru.myitschool.nasa_bootcamp.data.api.SpaceXApi
import ru.myitschool.nasa_bootcamp.data.db.AsteroidDao
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.AsteroidRepository
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXLaunchRepository
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXLaunchRepositoryImpl
import ru.myitschool.nasa_bootcamp.utils.NASA_BASE_URL
import ru.myitschool.nasa_bootcamp.utils.SPACEX_BASE_URL
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    @Named("NASA")
    fun getNasaRetrofit(): Retrofit{
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
    fun getSpaceXRetrofit(): Retrofit{
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(SPACEX_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    fun getNasaApi(@Named("NASA") retrofit: Retrofit): NasaApi{
        return retrofit.create(NasaApi::class.java)
    }

    @Provides
    @Singleton
    fun getSpaceXApi(@Named("SPACEX") retrofit: Retrofit): SpaceXApi{
        return retrofit.create(SpaceXApi::class.java)
    }

    @Provides
    @Singleton
    fun getAsteroidRepository(asteroidDao: AsteroidDao, nasaApi: NasaApi): AsteroidRepository{
        return AsteroidRepository(asteroidDao, nasaApi)
    }

    @Provides
    @Singleton
    fun getImageOfDayRepository(nasaApi: NasaApi): ImageOfDayRepository {
        return ImageOfDayRepositoryImpl(nasaApi)
    }

    @Provides
    @Singleton
    fun getSpaceXLaunchRepository(spaceXApi: SpaceXApi): SpaceXLaunchRepository {
        return SpaceXLaunchRepositoryImpl(spaceXApi)
    }

}