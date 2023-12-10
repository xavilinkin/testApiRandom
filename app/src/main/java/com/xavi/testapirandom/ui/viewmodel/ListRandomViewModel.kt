package com.xavi.testapirandom.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavi.testapirandom.data.model.RandomModel
import com.xavi.testapirandom.data.model.ResultUser
import com.xavi.testapirandom.domain.GetRandomUsersCase
import kotlinx.coroutines.launch

class ListRandomViewModel : ViewModel() {
    private val getRandomUsers = GetRandomUsersCase()
    private val listRandomUsers = MutableLiveData<Result<RandomModel?>?>()
    private val listSearchMLiveData = MutableLiveData<MutableList<ResultUser>>()
    private val listSearch = mutableListOf<ResultUser>()
    fun getListRandomUsers(): LiveData<Result<RandomModel?>?> = listRandomUsers
    fun getListSearchLiveData(): LiveData<MutableList<ResultUser>> = listSearchMLiveData

    fun onCreate(page: String) {
        viewModelScope.launch {
            listRandomUsers.postValue(getRandomUsers(page))
            getRandomUsers(page).getOrNull()
                ?.let {
                    it.results.let { it1 -> listSearch.addAll(it1) }
                    val filterList = listSearch.distinct().toMutableList()
                    listSearchMLiveData.postValue(filterList)
                }
        }
    }
}