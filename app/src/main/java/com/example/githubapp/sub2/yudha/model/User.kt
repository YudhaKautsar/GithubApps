package com.example.githubapp.sub2.yudha.model

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("login")
	val username: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String?,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("followers")
	val userFollowers: Int? = 0,

	@field:SerializedName("following")
	val userFollowing: Int? = 0
)
