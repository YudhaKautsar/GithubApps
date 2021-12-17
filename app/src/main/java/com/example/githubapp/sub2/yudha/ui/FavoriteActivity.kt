package com.example.githubapp.sub2.yudha.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.sub2.yudha.adapter.UserAdapter
import com.example.githubapp.sub2.yudha.databinding.ActivityFavoriteBinding
import com.example.githubapp.sub2.yudha.local.FavoriteUser
import com.example.githubapp.sub2.yudha.model.User
import com.example.githubapp.sub2.yudha.viewmodel.FavoriteViewModel
import com.example.githubapp.sub2.yudha.viewmodel.factory.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }

        })
        
        binding.apply {
            rvUserFav.setHasFixedSize(true)
            rvUserFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFav.adapter = adapter
        }

        setViewModel()
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers

    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(application)
        )[FavoriteViewModel::class.java]
        viewModel.getFavUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }
}