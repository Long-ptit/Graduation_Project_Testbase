package com.example.testbase.di

import com.example.testbase.network.Api
import com.example.testbase.service.ApiFcm
import com.example.testbase.util.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }


    @Provides
    @Singleton
    fun provideRetrofitApiFcm(client: OkHttpClient): ApiFcm {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL_FCM)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiFcm::class.java)
    }

}