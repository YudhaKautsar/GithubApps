package com.example.githubapp.sub2.yudha.ui

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.sub2.yudha.databinding.ActivitySettingsBinding
import com.example.githubapp.sub2.yudha.model.Reminder
import com.example.githubapp.sub2.yudha.preference.ReminderPreference
import com.example.githubapp.sub2.yudha.preference.ThemePreference
import com.example.githubapp.sub2.yudha.receiver.AlarmReceiver
import com.example.githubapp.sub2.yudha.viewmodel.SettingViewModel
import com.example.githubapp.sub2.yudha.viewmodel.factory.SettingViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAlarm()
        switchDarkMode()
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()

        reminder.isReminded = state
        reminderPreference.setReminder(reminder)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarm() {
        val reminderPreferences = ReminderPreference(this)

        binding.switchAlarm.isChecked = reminderPreferences.getReminder().isReminded

        alarmReceiver = AlarmReceiver()
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveReminder(true)
                alarmReceiver.setRepeatAlarm(this, "RepeatingAlarm","18:51",
                    "Github Reminder")
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun switchDarkMode(){

        val pref = ThemePreference.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        binding.apply {
            settingViewModel.getThemeSettings().observe(this@SettingsActivity, {

                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            })
            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                switchTheme.isChecked = isChecked
                settingViewModel.saveThemeSetting(isChecked)
            }
        }


    }
}