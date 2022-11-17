package com.vangelnum.stackoverflow.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vangelnum.stackoverflow.LibraryImage
import com.vangelnum.stackoverflow.presentation.FavouriteScreen
import com.vangelnum.stackoverflow.viewmodel.ViewModel

@Composable
fun Navigation(viewModel: ViewModel) {
    val navController = rememberNavController()
    val items = listOf(
        Screens.MainScreen,
        Screens.FavoriteScreen
    )
    Scaffold(
        bottomBar = {
            BottomNavigation {
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
                            Icon(painterResource(id = screen.icon), contentDescription = "icon")
                        })
                }
            }
        }) { innerPadding ->
        NavHost(navController = navController,
            startDestination = Screens.MainScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.MainScreen.route) {
                LibraryImage(viewModel = viewModel)
            }
            composable(route = Screens.FavoriteScreen.route) {
                FavouriteScreen(vm = viewModel)
            }
        }
    }


}