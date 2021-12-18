package com.example.githubapp.sub2.yudha.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.sub2.yudha.R
import com.example.githubapp.sub2.yudha.adapter.UserAdapter
import com.example.githubapp.sub2.yudha.databinding.ActivityMainBinding
import com.example.githubapp.sub2.yudha.model.User
import com.example.githubapp.sub2.yudha.preference.ThemePreference
import com.example.githubapp.sub2.yudha.viewmodel.MainViewModel
import com.example.githubapp.sub2.yudha.viewmodel.factory.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }

        })
        setViewModel()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            btnSearch.setOnClickListener {
                searchUser()
            }

            editSearch.setOnKeyListener { _, i, event ->
                if (event.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        getDarkTheme()
    }

    private fun setViewModel(){
        val pref = ThemePreference.getInstance(dataStore)
        viewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]
        viewModel.getSearchUser().observe(this, {
            if (it != null) {
                showLoading(false)
                adapter.setList(it)
            }
        })
    }
    private fun searchUser() {
        binding.apply {
            val query = editSearch.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.fav_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.settings_menu -> {
                Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getDarkTheme(){
        val pref = ThemePreference.getInstance(dataStore)
        val mainVIewModel = ViewModelProvider(this, MainViewModelFactory(pref)).get(
            MainViewModel::class.java
        )
        binding.apply {
            mainVIewModel.getThemeSettings().observe(this@MainActivity, {
                if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            })
        }
    }
}