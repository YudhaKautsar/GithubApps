package com.example.githubapp.sub2.yudha.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.githubapp.sub2.yudha.preference.ThemePreference
import com.example.githubapp.sub2.yudha.viewmodel.DetailUserViewModel
import com.example.githubapp.sub2.yudha.viewmodel.FavoriteViewModel
import com.example.githubapp.sub2.yudha.viewmodel.MainViewModel

class DetailViewModelFactory private constructor(private val application: Application, private val pref: ThemePreference) : NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(application, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}