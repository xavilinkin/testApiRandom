package com.xavi.testapirandom.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavi.testapirandom.data.model.RandomModel
import com.xavi.testapirandom.domain.GetRandomUsersCase
import kotlinx.coroutines.launch

class ListRandomViewModel : ViewModel() {
    private var getRandomUsers = GetRandomUsersCase()
    private val listRandomUsers = MutableLiveData<Result<RandomModel?>?>()
    fun getListRandomUsers(): LiveData<Result<RandomModel?>?> = listRandomUsers

    fun onCreate(page: String) {
        viewModelScope.launch {
            listRandomUsers.postValue(getRandomUsers(page))
        }
    }
}