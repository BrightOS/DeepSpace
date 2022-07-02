package ru.berserkers.deepspace.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.berserkers.deepspace.data.api.*
import ru.berserkers.deepspace.data.repository.*
import ru.berserkers.deepspace.utils.*
import java.net.InetAddress
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/*
 * @author Danil Khairulin
 */
@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    @Named("NASA")
    fun getNasaRetrofit(): Retrofit {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

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
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(SPACEX_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    @Named("SPACEX_NEW")
    fun getSpaceXNewRetrofit(): Retrofit {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(SPACEX_BASE_V5_URL)
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
    @Named("SPACEX_LAUNCH")
    fun getLaunchRetrofit(): Retrofit {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(SPACEX_LAUNCH_URL)
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
//
//    @Provides
//    @Singleton
//    fun getSpaceXNewApi(@Named("SPACEX_NEW") retrofit: Retrofit): SpaceXApi {
//        return retrofit.create(SpaceXApi::class.java)
//    }

    @Provides
    @Singleton
    fun getUpcomingEventsApi(@Named("UPCOMING") retrofit: Retrofit): UpcomingEventsApi {
        return retrofit.create(UpcomingEventsApi::class.java)
    }

    @Provides
    @Singleton
    fun getSpaceXLaunchApi(@Named("SPACEX_LAUNCH") retrofit: Retrofit): LaunchApi {
        return retrofit.create(LaunchApi::class.java)
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
    fun getFirebaseRepository(@ApplicationContext appContext: Context): FirebaseRepository {
        return FirebaseRepositoryImpl(appContext)
    }

    @Provides
    @Singleton
    fun getNetworkRepository(
        firebaseRepository: FirebaseRepository,
        newsRepository: NewsRepository
    ): SocialMediaRepository {
        return SocialMediaRepositoryImpl(firebaseRepository, newsRepository)
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
    fun getUpcomingRepository(upcomingEventsApi: UpcomingEventsApi): UpcomingRepository {
        return UpcomingRepositoryImpl(upcomingEventsApi)
    }

    private fun checkInternetConnection(): Boolean {
        return try {
            val inAddress: InetAddress = InetAddress.getByName("http://google.com")
            !inAddress.equals("")
        } catch (e: java.lang.Exception) {
            false
        }
    }
}
