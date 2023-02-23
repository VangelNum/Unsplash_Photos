package com.vangelnum.unsplash.room

import androidx.lifecycle.LiveData

class PhotoRepository(private val photoDatabase: PhotoDao) {
    val readAllData: LiveData<List<PhotoItem>> = photoDatabase.getAll()

    fun addPhoto(photoItem: PhotoItem) {
        photoDatabase.addPhoto(photoItem)
    }

    fun updatePhoto(photoItem: PhotoItem) {
        photoDatabase.updatePhoto(photoItem)
    }

    fun deletePhoto(photoItem: PhotoItem) {
        photoDatabase.deletePhoto(photoItem)
    }

    fun deletePhotoByUrl(url: String) {
        photoDatabase.deletePhoto(url)
    }

}