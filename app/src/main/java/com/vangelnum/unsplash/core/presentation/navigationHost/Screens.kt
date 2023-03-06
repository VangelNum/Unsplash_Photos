package com.vangelnum.unsplash.core.presentation.navigationHost

import com.vangelnum.unsplash.R

sealed class Screens(val icon: Int, val route: String, val name: String) {
    object MainScreen : Screens(R.drawable.ic_baseline_home_24, "latest_screen","Latest")
    object RandomScreen: Screens(R.drawable.random,"random_screen", "Random")
    object PopularScreen: Screens(R.drawable.ic_baseline_local_fire_department_24,"popular_screen","Popular")
    object WatchPhotoScreen : Screens(R.drawable.ic_baseline_favorite_24, "watch_screen", "Watch")
    object SearchScreen: Screens(R.drawable.ic_outline_search_24,"search_screen","Search")
    object SearchCollectionScreen: Screens(R.drawable.ic_outline_search_24,"search_collection","Search_collection")

    object NavigationScreen: Screens(R.drawable.ic_baseline_home_24,"main_screen","Main")
    object CollectionsScreen: Screens(R.drawable.baseline_collections_24,"collections","Collections")
    object FavoriteScreen : Screens(R.drawable.ic_baseline_favorite_24, "favorite_screen","Favourite")

    object ContactScreen : Screens(R.drawable.ic_baseline_favorite_24, "contact_screen","Contact")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

