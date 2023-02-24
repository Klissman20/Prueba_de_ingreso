package com.example.pruebadeingreso.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pruebadeingreso.domain.model.User

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "email") var email: String
)
fun User.toDatabase() = UserEntity(id = id, name = name, phone = phone, email = email)