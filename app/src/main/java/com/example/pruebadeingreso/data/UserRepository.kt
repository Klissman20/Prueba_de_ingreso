package com.example.pruebadeingreso.data

import com.example.pruebadeingreso.data.database.dao.UserDao
import com.example.pruebadeingreso.data.database.entities.UserEntity
import com.example.pruebadeingreso.data.model.PublishModel
import com.example.pruebadeingreso.data.model.UserModel
import com.example.pruebadeingreso.data.network.ApiService
import com.example.pruebadeingreso.domain.model.User
import com.example.pruebadeingreso.domain.model.toDomain
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService,
    private val userDao: UserDao
) {

    suspend fun getAllUserFromApi(): List<User> {
        val response: List<UserModel> = api.getUsers()
        return response.map { it.toDomain() }
    }

    suspend fun getPublishFromApi(query: String): List<PublishModel> {
        return api.getUsersPublish(query)
    }

    suspend fun getAllUsersFromDatabase():List<User>{
        val response: List<UserEntity> = userDao.getAll()
        return response.map { it.toDomain() }
    }

    suspend fun getUsersByName(query: String):List<User>{
        val response: List<UserEntity> = userDao.findByName(query)
        return response.map { it.toDomain() }
    }

    suspend fun insertUsers(quotes:List<UserEntity>){
        userDao.insertAll(quotes)
    }

    suspend fun clearUsers(){
        userDao.deleteAllUsers()
    }
}