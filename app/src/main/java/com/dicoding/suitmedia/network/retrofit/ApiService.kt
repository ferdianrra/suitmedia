package com.dicoding.suitmedia.network.retrofit

import com.dicoding.suitmedia.network.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ):UserResponse
}