package com.example.githubapp.sub2.yudha.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubapp.sub2.yudha.api.ApiConfig
import com.example.githubapp.sub2.yudha.model.User
import com.example.githubapp.sub2.yudha.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUser(username: String){
        ApiConfig.getApiService().getSearchUser(username)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<User>>{
        return listUsers
    }

}