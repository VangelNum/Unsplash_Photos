package com.vangelnum.unsplash.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE

@Dao
interface PhotoDao {

    @Query("SELECT * from entity_table")
    fun getAll(): LiveData<List<PhotoItem>>

    @Insert(onConflict = REPLACE)
    fun addPhoto(photo: PhotoItem)

    @Update
    fun updatePhoto(photo: PhotoItem)

    @Delete
    fun deletePhoto(photo: PhotoItem)

    @Query("DELETE FROM entity_table WHERE urlPhoto = :urlPhoto")
    fun deletePhoto(urlPhoto: String)
}