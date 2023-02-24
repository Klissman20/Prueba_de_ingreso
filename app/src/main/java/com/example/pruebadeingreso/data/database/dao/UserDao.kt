package com.example.pruebadeingreso.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebadeingreso.data.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM Users WHERE name LIKE :name")
    suspend fun findByName(name: String): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users:List<UserEntity>)


}