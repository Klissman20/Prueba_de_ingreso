package com.example.pruebadeingreso.domain.model

import com.example.pruebadeingreso.data.database.entities.UserEntity
import com.example.pruebadeingreso.data.model.UserModel

data class User (var id: Int, var name: String, var phone: String, var email: String)

fun UserModel.toDomain() = User(id, name, phone, email)
fun UserEntity.toDomain() = User(id, name, phone, email)