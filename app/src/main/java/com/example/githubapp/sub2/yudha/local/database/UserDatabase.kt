package com.example.githubapp.sub2.yudha.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubapp.sub2.yudha.local.FavoriteUser
import com.example.githubapp.sub2.yudha.local.FavoriteUserDao

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {

    companion object{
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase?{
            if (INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favUserDao(): FavoriteUserDao
}