package com.vangelnum.stackoverflow.presentation.navigation

import com.vangelnum.stackoverflow.R

sealed class Screens(val icon: Int, val route: String) {
    object MainScreen : Screens(R.drawable.ic_baseline_home_24, "main_screen")
    object FavoriteScreen : Screens(R.drawable.ic_baseline_favorite_24, "favorite_screen")
    object WatchPhotoScreen : Screens(R.drawable.ic_baseline_favorite_24, "watch_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

