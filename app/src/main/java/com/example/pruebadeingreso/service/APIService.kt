package com.example.pruebadeingreso.service

import com.example.pruebadeingreso.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getUserInformation(@Url url:String): Response<List<User>>
}