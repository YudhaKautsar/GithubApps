package com.example.githubapp.sub2.yudha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubapp.sub2.yudha.preference.ThemePreference

class SplashViewModel(private val pref: ThemePreference): ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}