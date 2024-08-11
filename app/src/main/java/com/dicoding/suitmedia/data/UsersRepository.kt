package com.dicoding.suitmedia.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.suitmedia.network.response.UserItem
import com.dicoding.suitmedia.network.retrofit.ApiService

class UsersRepository(private val apiService: ApiService) {

    fun getUsers(): LiveData<PagingData<UserItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 8,
                initialLoadSize = 8,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }
}