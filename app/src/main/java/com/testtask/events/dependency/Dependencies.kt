package com.testtask.events.dependency

import androidx.room.Room
import com.testtask.events.App
import com.testtask.events.BuildConfig
import com.testtask.events.api.GithubServiceApi
import com.testtask.events.db.AppDatabase
import com.testtask.events.network.NetworkSource
import com.testtask.events.network.NetworkSourceImpl
import com.testtask.events.repository.EventsMapper
import com.testtask.events.repository.EventsRepository
import com.testtask.events.repository.EventsRepositoryImpl
import com.testtask.events.repository.cache.EventsCache
import com.testtask.events.repository.cache.EventsCacheImpl
import com.testtask.events.repository.cache.SharedPreferencesImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.github.com/"
private const val NAME_DB = "events.db"

object Dependencies {

    private val db by lazy {
        createRoomDatabase()
    }

    val eventsRepository by lazy {
        createMoviesRepository()
    }

    val eventsMapper by lazy {
        createEventsMapper()
    }

    private fun createMoviesRepository(): EventsRepository {
        return EventsRepositoryImpl(
            createGithubServiceApi(),
            createNetworkSource(),
            createEventsCache()
        )
    }

    private fun createGithubServiceApi(): GithubServiceApi {
        return createRetrofit().create()
    }

    private fun createNetworkSource(): NetworkSource {
        return NetworkSourceImpl()
    }

    private fun createEventsMapper(): EventsMapper {
        return EventsMapper()
    }

    private fun createEventsCache(): EventsCache {
        return EventsCacheImpl(
            db.responseDao(),
            SharedPreferencesImpl(App.instance)
        )
    }

    private fun getOkHttpClient() =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(createLoggingInterceptor())
            .build()

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        if (BuildConfig.DEBUG) {
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return HttpLoggingInterceptor()
    }

    private fun createRetrofit() =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(getOkHttpClient())
            .build()

    private fun createRoomDatabase() =
        Room.databaseBuilder(
            App.instance,
            AppDatabase::class.java,
            NAME_DB
        ).build()

}