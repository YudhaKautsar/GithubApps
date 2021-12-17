package com.example.githubapp.sub2.yudha.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.githubapp.sub2.yudha.viewmodel.DetailUserViewModel

class DetailViewModelFactory(private val application: Application) : NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}