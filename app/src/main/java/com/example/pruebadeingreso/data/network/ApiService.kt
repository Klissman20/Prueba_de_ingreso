package com.example.pruebadeingreso.data.network

import com.example.pruebadeingreso.data.model.PublishModel
import com.example.pruebadeingreso.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(private val api:ApiClient){
    suspend fun getUsers(): List<UserModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllUsers()
            response.body() ?: emptyList()
        }
    }

    suspend fun getUsersPublish(query: String): List<PublishModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getUserPublish(query)
            response.body() ?: emptyList()
        }
    }
}