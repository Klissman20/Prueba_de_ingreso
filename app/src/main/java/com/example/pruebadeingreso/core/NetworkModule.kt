package com.example.pruebadeingreso.core

import com.example.pruebadeingreso.data.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    //A unique Retrofit instance is provided
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //A unique ApiClient instance is provided
    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiClient{
        return retrofit.create(ApiClient::class.java)
    }

}