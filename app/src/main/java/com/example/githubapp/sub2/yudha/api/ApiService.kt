package com.example.githubapp.sub2.yudha.api

import com.example.githubapp.sub2.yudha.BuildConfig
import com.example.githubapp.sub2.yudha.model.User
import com.example.githubapp.sub2.yudha.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUser(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>


    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}