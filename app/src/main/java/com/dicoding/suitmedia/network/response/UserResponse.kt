package com.dicoding.suitmedia.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("data")
	val user: List<UserItem>,
)

data class UserItem(

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("email")
	val email: String
)

