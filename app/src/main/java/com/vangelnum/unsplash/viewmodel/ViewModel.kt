package com.vangelnum.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vangelnum.unsplash.room.PhotoDatabase
import com.vangelnum.unsplash.room.PhotoItem
import com.vangelnum.unsplash.room.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModel(application: Application) : AndroidViewModel(application) {

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

    fun deletePhotoByUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePhotoByUrl(url)
        }
    }

}