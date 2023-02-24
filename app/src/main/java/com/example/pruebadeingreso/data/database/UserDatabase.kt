package com.example.pruebadeingreso.data.database

import androidx.room.*
import com.example.pruebadeingreso.data.database.dao.UserDao
import com.example.pruebadeingreso.data.database.entities.UserEntity

@Database(version = 1, entities = [UserEntity::class], exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

}