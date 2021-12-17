package com.example.githubapp.sub2.yudha.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapp.sub2.yudha.adapter.SectionPagerAdapter
import com.example.githubapp.sub2.yudha.databinding.ActivityDetailUserBinding
import com.example.githubapp.sub2.yudha.viewmodel.DetailUserViewModel
import com.example.githubapp.sub2.yudha.viewmodel.factory.DetailViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val photo = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        setViewModel(username, id, photo, bundle)


    }

    @SuppressLint("SetTextI18n")
    private fun setViewModel(username: String?, id: Int, photo: String?, bundle: Bundle){
        viewModel = ViewModelProvider(this, DetailViewModelFactory(application))[DetailUserViewModel::class.java]
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    accName.text = it.name
                    accUsername.text = "@ ${it.username}"
                    accFollowers.text = "${it.userFollowers} Followers"
                    accFollowing.text = "${it.userFollowing} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .into(accProfile)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    binding.apply {
                        if (count > 0) {
                            buttonFav.isChecked = true
                            _isChecked = true
                        } else {
                            buttonFav.isChecked = false
                            _isChecked = false
                        }
                    }
                }
            }
        }

        binding.buttonFav.setOnClickListener {
            if (!_isChecked){
                viewModel.addToFav(username, id, photo)
            } else {
                viewModel.removeFromFav(id)
            }
            binding.buttonFav.isChecked = !_isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager   )
        }
    }

}