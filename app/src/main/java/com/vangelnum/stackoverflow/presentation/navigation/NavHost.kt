package com.vangelnum.stackoverflow.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vangelnum.stackoverflow.LibraryImage
import com.vangelnum.stackoverflow.presentation.FavouriteScreen
import com.vangelnum.stackoverflow.presentation.PopularScreen
import com.vangelnum.stackoverflow.presentation.WatchPhoto
import com.vangelnum.stackoverflow.room.PhotoItem
import com.vangelnum.stackoverflow.viewmodel.ViewModel

@Composable
fun Navigation(viewModel: ViewModel) {
    val navController = rememberNavController()
    val items = listOf(
        Screens.MainScreen,
        Screens.RandomScreen,
        Screens.PopularScreen,
        Screens.FavoriteScreen
    )

    val itemsFavouritePhotos: List<PhotoItem> = viewModel.readAllData.observeAsState(listOf()).value

    Scaffold(
        bottomBar = {
            BottomNavigation(modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp,
                topEnd = 25.dp))) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painterResource(id = screen.icon),
                                contentDescription = "icon",
                                modifier = Modifier.size(24.dp)
                            )
                        })
                }
            }
        }) { innerPadding ->
        NavHost(navController = navController,
            startDestination = Screens.MainScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.MainScreen.route) {
                LibraryImage(viewModel = viewModel, navController = navController,itemsFavouritePhotos = itemsFavouritePhotos)
            }
            composable(route = Screens.RandomScreen.route) {
                RandomPhotos(
                    viewModel = viewModel,
                    navController = navController,
                    itemsFavouritePhotos = itemsFavouritePhotos
                )
            }
            composable(route = Screens.FavoriteScreen.route) {
                FavouriteScreen(navController = navController,items = itemsFavouritePhotos)
            }
            composable(
                route = Screens.WatchPhotoScreen.route + "/{url}",
                arguments = listOf(
                    navArgument("url") {
                        NavType.StringType
                        nullable = false
                    }
                )
            ) { entry ->
                WatchPhoto(entry.arguments?.getString("url"), viewModel = viewModel)
            }
            composable(route = Screens.PopularScreen.route) {
                PopularScreen(viewModel = viewModel, navController = navController,itemsFavouritePhotos = itemsFavouritePhotos)
            }
        }
    }


}