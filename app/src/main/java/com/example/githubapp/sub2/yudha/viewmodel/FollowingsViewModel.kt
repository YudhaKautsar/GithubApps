package com.example.githubapp.sub2.yudha.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubapp.sub2.yudha.api.ApiConfig
import com.example.githubapp.sub2.yudha.model.User
import com.example.githubapp.sub2.yudha.preference.ThemePreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingsViewModel(private val pref: ThemePreference): ViewModel() {
    val listFollowings = MutableLiveData<ArrayList<User>>()

    fun setListFollowings(username: String){
        ApiConfig.getApiService().getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listFollowings.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowings(): LiveData<ArrayList<User>>{
        return listFollowings
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}