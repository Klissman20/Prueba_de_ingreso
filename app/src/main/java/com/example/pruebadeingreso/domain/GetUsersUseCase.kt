package com.example.pruebadeingreso.domain

import com.example.pruebadeingreso.data.UserRepository
import com.example.pruebadeingreso.data.database.entities.toDatabase
import com.example.pruebadeingreso.domain.model.User
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke():List<User> {
        var users = repository.getAllUsersFromDatabase()
        return if(users.isEmpty()){
            users = repository.getAllUserFromApi()
            repository.insertUsers(users.map { it.toDatabase() })
            users
        }else{
            users
        }
    }
}