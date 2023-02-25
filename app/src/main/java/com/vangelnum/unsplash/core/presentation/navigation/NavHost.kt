package com.vangelnum.unsplash.core.presentation.navigation

import FavouriteScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vangelnum.unsplash.feature_main.presentation.MainScreen
import com.vangelnum.unsplash.feature_popular.presentation.PopularScreen
import com.vangelnum.unsplash.feature_random.presentation.RandomScreen
import com.vangelnum.unsplash.feature_search.presentation.SearchScreen
import com.vangelnum.unsplash.feature_watch.WatchPhoto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            if (currentDestination?.route == Screens.NavigationScreen.route) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Outlined.Menu, contentDescription = null)
                        }
                    }
                )
            } else {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Outlined.Menu, contentDescription = null)
                        }
                    }
                )
            }
//            if (currentDestination?.route != Screens.WatchPhotoScreen.route + "/{url}" + "/{id}") {
//                TopAppBar(
//                    title = {
//
//                    },
//                    navigationIcon = {
//                        IconButton(onClick = {}) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_baseline_menu_24),
//                                contentDescription = "menu"
//                            )
//                        }
//                    },
//                    elevation = 0.dp,
//                    backgroundColor = Color.Transparent,
//                )
//            } else {
//                TopAppBar(title = { },
//                    navigationIcon = {
//                        IconButton(onClick = {
//                            navController.popBackStack()
//                        }) {
//                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
//                        }
//                    }
//                )
//            }
        },
        bottomBar = {
            MyBottomNavigation(
                navController = navController,
                currentDestination = currentDestination
            )
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.MainScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.MainScreen.route) {
                MainScreen(navController = navController)
            }
            composable(route = Screens.RandomScreen.route) {
                RandomScreen()
            }
            composable(route = Screens.FavoriteScreen.route) {
                FavouriteScreen(navController = navController)
            }
            composable(
                route = Screens.WatchPhotoScreen.route + "/{url}" + "/{id}",
                arguments = listOf(
                    navArgument("url") {
                        NavType.StringType
                        nullable = false
                    },
                    navArgument("id") {
                        NavType.StringType
                        nullable = false
                    }
                )
            ) { entry ->
                WatchPhoto(entry.arguments?.getString("url"), entry.arguments?.getString("id"))
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


@Composable
fun MyBottomNavigation(
    navController: NavController,
    currentDestination: NavDestination?
) {
    val items = listOf(
        Screens.MainScreen,
        Screens.RandomScreen,
        Screens.PopularScreen,
        Screens.SearchScreen,
        Screens.FavoriteScreen
    )

    if (navController.currentDestination?.route in items.map { it.route }) {
        NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
    }
}

