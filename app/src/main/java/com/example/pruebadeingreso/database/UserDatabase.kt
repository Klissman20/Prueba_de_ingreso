package com.example.pruebadeingreso.database

import android.content.Context
import androidx.room.*
import com.example.pruebadeingreso.model.User

@Database(version = 1, entities = [User::class], exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, "user_db").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}