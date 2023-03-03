package com.vangelnum.unsplash.feature_collections.presentation

import androidx.compose.ui.graphics.Color

data class CollectionItems(
    val photoId: Int,
    val name: String
)


data class ColorItems(val color: Color, val name: String)
