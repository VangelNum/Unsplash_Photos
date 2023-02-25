package com.vangelnum.unsplash.feature_favourite.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vangelnum.unsplash.feature_favourite.domain.model.FavouriteItem

@Database(entities = [FavouriteItem::class], version = 4)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun getDao(): FavouriteDao
}