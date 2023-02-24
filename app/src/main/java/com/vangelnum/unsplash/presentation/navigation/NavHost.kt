package com.vangelnum.unsplash.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.feature_main.presentation.MainScreen
import com.vangelnum.unsplash.feature_popular.presentation.PopularScreen
import com.vangelnum.unsplash.feature_random.presentation.RandomScreen
import com.vangelnum.unsplash.feature_search.presentation.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    val items = listOf(
        Screens.MainScreen,
        Screens.RandomScreen,
        Screens.PopularScreen,
        Screens.SearchScreen,
        Screens.FavoriteScreen
    )

    //val itemsFavouritePhotos: List<PhotoItem> = viewModel.readAllData.observeAsState(listOf()).value


    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                            contentDescription = "menu"
                        )
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
            )
        },
        bottomBar = {
            BottomNavigation(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp
                    )
                )
            ) {
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
                        },
                        label = {
                            Text(text = screen.name)
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.MainScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.MainScreen.route) {
                MainScreen()
            }
            composable(route = Screens.RandomScreen.route) {
                RandomScreen()
            }
            composable(route = Screens.FavoriteScreen.route) {
                // FavouriteScreen(navController = navController, items = itemsFavouritePhotos)
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
                //  WatchPhoto(entry.arguments?.getString("url"), viewModel = viewModel)
            }
            composable(route = Screens.PopularScreen.route) {
                PopularScreen()
            }
            composable(
                route = Screens.SearchScreen.route
            ) {
                SearchScreen()
            }

        }
    }


}