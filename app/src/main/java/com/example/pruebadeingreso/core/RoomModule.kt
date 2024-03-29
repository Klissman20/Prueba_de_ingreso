package com.example.pruebadeingreso.core

import android.content.Context
import androidx.room.Room
import com.example.pruebadeingreso.data.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val USER_DATABASE_NAME = "user_database"
    //A unique RoomDB instance is provided
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java, USER_DATABASE_NAME
        ).allowMainThreadQueries().build()

    ////A unique DatabaseDao instance is provided
    @Singleton
    @Provides
    fun provideUserDao(db: UserDatabase) = db.getUserDao()
}