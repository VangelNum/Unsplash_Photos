package com.vangelnum.stackoverflow.navigation

import com.vangelnum.stackoverflow.R

sealed class Screen(var icon: Int, val route: String) {
    object MainScreen: Screen(R.drawable.ic_baseline_home_24,"main_screen")
    object FavoriteScreen: Screen(R.drawable.ic_baseline_favorite_24,"favorite_screen")
}

