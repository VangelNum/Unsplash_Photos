package com.vangelnum.stackoverflow.room

import androidx.lifecycle.LiveData

class PhotoRepository(private val photoDatabase: PhotoDao) {
    val readAllData: LiveData<List<PhotoItem>> = photoDatabase.getAll()

    suspend fun addPhoto(photoItem: PhotoItem) {
        photoDatabase.addPhoto(photoItem)
    }

    suspend fun updatePhoto(photoItem: PhotoItem) {
        photoDatabase.updatePhoto(photoItem)
    }

    suspend fun deletePhoto(photoItem: PhotoItem) {
        photoDatabase.deletePhoto(photoItem)
    }

    suspend fun deletePhotoByUrl(url: String) {
        photoDatabase.deletePhoto(url)
    }

}