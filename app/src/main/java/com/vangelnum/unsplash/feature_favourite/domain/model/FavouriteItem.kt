package com.vangelnum.unsplash.feature_favourite.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class FavouriteItem(
    @PrimaryKey(autoGenerate = false)
    val idPhoto: String,
    val urlPhoto: String
)
