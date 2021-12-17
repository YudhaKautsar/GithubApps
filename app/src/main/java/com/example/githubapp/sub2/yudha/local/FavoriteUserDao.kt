package com.example.githubapp.sub2.yudha.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {

    @Insert
    fun addToFav(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM fav_user")
    fun getFavUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM fav_user WHERE fav_user.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM fav_user WHERE fav_user.id = :id")
    fun removeFromFav(id: Int): Int
}