package com.example.pruebadeingreso.data.network

import com.example.pruebadeingreso.data.model.PublishModel
import com.example.pruebadeingreso.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiClient {
    @GET("/users")
    suspend fun getAllUsers(): Response<List<UserModel>>
    @GET
    suspend fun getUserPublish(@Url url:String): Response<List<PublishModel>>
}