package com.dicoding.suitmedia.di

import android.content.Context
import com.dicoding.suitmedia.data.UsersRepository
import com.dicoding.suitmedia.network.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUsersRepository(): UsersRepository {
        val apiService = ApiConfig.getApiService()
        return UsersRepository(apiService)
    }
}