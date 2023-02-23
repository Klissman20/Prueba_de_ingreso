package com.example.pruebadeingreso.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    fun getAll(): List<User>

    @Query("SELECT * FROM Users WHERE name LIKE :name")
    fun findByName(name: String): List<User>

    @Insert
    fun insertAll(vararg users: User)

}