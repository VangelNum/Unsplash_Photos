package com.vangelnum.unsplash.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PhotoItem::class], version = 3)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun todoDao(): PhotoDao

    companion object {
        private var INSTANCE: PhotoDatabase? = null
        fun getInstance(context: Context): PhotoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PhotoDatabase::class.java,
                        "todo_list_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}