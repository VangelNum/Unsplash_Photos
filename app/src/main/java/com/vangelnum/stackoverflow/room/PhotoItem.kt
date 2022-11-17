package com.vangelnum.stackoverflow.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entity_table")
data class PhotoItem(
    @PrimaryKey(autoGenerate = true)
    val idPhoto: Int,
    val urlPhoto: String
)
