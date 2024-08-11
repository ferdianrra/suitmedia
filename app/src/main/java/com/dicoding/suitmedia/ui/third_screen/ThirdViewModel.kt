package com.dicoding.suitmedia.ui.third_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.suitmedia.data.UsersRepository
import com.dicoding.suitmedia.network.response.UserItem
import kotlinx.coroutines.launch

class ThirdViewModel(private var usersRepository: UsersRepository): ViewModel() {
    private val _listUser = MutableLiveData<PagingData<UserItem>>()
    val listUser : LiveData<PagingData<UserItem>> = _listUser

    init {
        refreshPages()
    }

    fun refreshPages() {
        viewModelScope.launch {
            usersRepository.getUsers().cachedIn(viewModelScope).observeForever { data ->
                _listUser.postValue(data)
            }
        }
    }
}