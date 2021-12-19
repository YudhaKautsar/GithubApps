package com.example.githubapp.sub2.yudha.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.sub2.yudha.databinding.ActivitySplashBinding
import com.example.githubapp.sub2.yudha.preference.ThemePreference
import com.example.githubapp.sub2.yudha.viewmodel.SplashViewModel
import com.example.githubapp.sub2.yudha.viewmodel.factory.SplashViewModelFactory
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_DELAY: Long = 2000
    private lateinit var splashViewModel: SplashViewModel
    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewModel()
        getDarkTheme()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityScope.launch {
            delay(SPLASH_DELAY)
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onRestart() {
        activityScope.cancel()
        super.onRestart()
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

    private fun setViewModel(){
        val pref = ThemePreference.getInstance(dataStore)
         splashViewModel = ViewModelProvider(this, SplashViewModelFactory(pref)).get(
            SplashViewModel::class.java
        )
    }
    private fun getDarkTheme(){
        splashViewModel.getThemeSettings().observe(this, {
            if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        })

    }
}