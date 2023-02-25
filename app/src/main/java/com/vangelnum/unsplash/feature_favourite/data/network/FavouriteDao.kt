package com.vangelnum.unsplash.feature_favourite.data.network

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.IGNORE
import com.vangelnum.unsplash.feature_favourite.domain.model.FavouriteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * from favourite_table")
    fun getAll(): Flow<List<FavouriteItem>>

    @Insert(onConflict = IGNORE)
    suspend fun addPhoto(photo: FavouriteItem)

    @Delete
    suspend fun deletePhoto(photo: FavouriteItem)
}