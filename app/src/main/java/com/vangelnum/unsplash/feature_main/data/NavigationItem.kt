package com.vangelnum.unsplash.feature_main.data

import androidx.annotation.DrawableRes

data class NavigationItem(
    val text: String,
    @DrawableRes val image: Int,
    val routeMain: String
)
