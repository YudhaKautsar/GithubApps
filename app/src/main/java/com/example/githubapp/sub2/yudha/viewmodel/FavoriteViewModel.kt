package com.example.githubapp.sub2.yudha.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.sub2.yudha.local.FavoriteUser
import com.example.githubapp.sub2.yudha.local.FavoriteUserDao
import com.example.githubapp.sub2.yudha.local.database.UserDatabase
import com.example.githubapp.sub2.yudha.preference.ThemePreference
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application, private val pref: ThemePreference): AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favUserDao()
    }

    fun getFavUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavUser()
    }

}