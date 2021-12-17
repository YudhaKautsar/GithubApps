package com.example.githubapp.sub2.yudha.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapp.sub2.yudha.api.ApiConfig
import com.example.githubapp.sub2.yudha.local.FavoriteUser
import com.example.githubapp.sub2.yudha.local.FavoriteUserDao
import com.example.githubapp.sub2.yudha.local.database.UserDatabase
import com.example.githubapp.sub2.yudha.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<User>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favUserDao()
    }

    fun setUserDetail(username: String) {
        ApiConfig.getApiService().getDetailUser(username)
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<User> {
        return user
    }

    fun addToFav(username: String?, id: Int, avatarUrl: String?){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFav(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFav(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFav(id)
        }
    }

}