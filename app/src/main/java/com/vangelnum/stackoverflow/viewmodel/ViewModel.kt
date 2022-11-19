package com.vangelnum.stackoverflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vangelnum.stackoverflow.pagination.PageSource
import com.vangelnum.stackoverflow.pagination.PageSourceForPopular
import com.vangelnum.stackoverflow.pagination.PageSourceForRandom
import com.vangelnum.stackoverflow.room.PhotoDatabase
import com.vangelnum.stackoverflow.room.PhotoItem
import com.vangelnum.stackoverflow.room.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModel(application: Application) : AndroidViewModel(application) {
    val pager = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            PageSource()
        }
    ).flow.cachedIn(viewModelScope)

    val pagerRandom = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            PageSourceForRandom()
        }
    ).flow.cachedIn(viewModelScope)

    val pagerPopular = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            PageSourceForPopular()
        }
    ).flow.cachedIn(viewModelScope)

    val readAllData: LiveData<List<PhotoItem>>
    private val repository: PhotoRepository

    init {
        val todoDao = PhotoDatabase.getInstance(application).todoDao()
        repository = PhotoRepository(todoDao)
        readAllData = repository.readAllData
    }

    fun addPhoto(photoItem: PhotoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPhoto(photoItem)
        }
    }

    fun updatePhoto(todoItem: PhotoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePhoto(photoItem = todoItem)
        }
    }

    fun deletePhoto(todoItem: PhotoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePhoto(photoItem = todoItem)
        }
    }

    fun deletePhotoByUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePhotoByUrl(url)
        }
    }

}