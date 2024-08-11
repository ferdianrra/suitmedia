package com.dicoding.suitmedia.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.suitmedia.network.response.UserItem
import com.dicoding.suitmedia.network.retrofit.ApiService
import java.lang.Error

class UserPagingSource (private val apiService: ApiService): PagingSource<Int, UserItem>() {

    override fun getRefreshKey(state: PagingState<Int, UserItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage =  state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getUser(position, params.loadSize)
            val user = responseData.user
            LoadResult.Page(
                data = user,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (user.isEmpty()) null else position+1
            )
        } catch (exception: Exception) {
            Log.e(TAG, Error(exception).toString())
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "UserPagingSource"
    }
}