package com.vangelnum.stackoverflow.presentation.navigation

import com.vangelnum.stackoverflow.R

sealed class Screens(val icon: Int, val route: String) {
    object MainScreen : Screens(R.drawable.ic_baseline_home_24, "main_screen")
    object RandomScreen: Screens(R.drawable.random,"random_screen")
    object PopularScreen: Screens(R.drawable.ic_baseline_local_fire_department_24,"popular_screen")
    object FavoriteScreen : Screens(R.drawable.ic_baseline_favorite_24, "favorite_screen")
    object WatchPhotoScreen : Screens(R.drawable.ic_baseline_favorite_24, "watch_screen")
    object SearchScreen: Screens(R.drawable.ic_outline_search_24,"search_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

