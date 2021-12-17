package com.example.githubapp.sub2.yudha.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.githubapp.sub2.yudha.preference.ThemePreference
import com.example.githubapp.sub2.yudha.viewmodel.MainViewModel

class MainViewModelFactory(private val pref: ThemePreference) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}