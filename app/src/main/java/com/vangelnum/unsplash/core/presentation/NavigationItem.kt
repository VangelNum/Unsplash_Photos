package com.vangelnum.unsplash.core.presentation

import androidx.annotation.DrawableRes

data class NavigationItem(
    val text: String,
    @DrawableRes val image: Int,
    val routeMain: String
)
