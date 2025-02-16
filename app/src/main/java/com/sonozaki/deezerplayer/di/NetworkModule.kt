package com.sonozaki.deezerplayer.di

import com.sonozaki.deezertracks.network.services.ChartService
import com.sonozaki.deezertracks.network.services.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit): SearchService = retrofit.create(
        SearchService::class.java
    )

    @Provides
    @Singleton
    fun provideChartService(retrofit: Retrofit): ChartService =
        retrofit.create(ChartService::class.java)

    companion object {
        private const val BASE_URL = "https://api.deezer.com"
    }
}