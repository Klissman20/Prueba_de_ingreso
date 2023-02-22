package com.example.pruebadeingreso.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [User::class])
abstract class UserDatabase : RoomDatabase() {
    // SongDao is a class annotated with @Dao.
    abstract fun getUserDao()

}