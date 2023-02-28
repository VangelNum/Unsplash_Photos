package com.vangelnum.unsplash.core.presentation.navigation

import FavouriteScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.core.presentation.NavigationScreen
import com.vangelnum.unsplash.feature_collections.presentation.CollectionSearchScreen
import com.vangelnum.unsplash.feature_collections.presentation.CollectionsScreen
import com.vangelnum.unsplash.feature_latest.presentation.MainScreen
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

    val itemsScreens = listOf(
        Screens.NavigationScreen,
        Screens.CollectionsScreen,
        Screens.FavoriteScreen
    )

    Scaffold(
        topBar = {
            val currentRoute = navController.currentDestination?.route
            val itemsRoutes = itemsScreens.map {
                it.route
            }
            if (currentRoute != null && currentRoute !in itemsRoutes) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = currentDestination?.route.toString(),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                        }
                    }
                )
            } else {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.wallpaper),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = "menu"
                            )
                        }
                    },
                )
            }
        },
        bottomBar = {
            MyBottomNavigation(
                navController = navController,
                currentDestination = currentDestination,
                items = itemsScreens
            )
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.NavigationScreen.route,
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
                PopularScreen(navController)
            }
            composable(
                route = Screens.SearchScreen.route
            ) {
                SearchScreen()
            }
            composable(route = Screens.NavigationScreen.route) {
                NavigationScreen(navController)
            }
            composable(route = Screens.CollectionsScreen.route) {
                CollectionsScreen(navController)
            }
            composable(route = Screens.SearchCollectionScreen.route + "/{query}",
                arguments = listOf(
                    navArgument("query") {
                        NavType.StringType
                        nullable = false
                    }
                )) { entry ->
                CollectionSearchScreen(
                    query = entry.arguments?.getString("query"),
                    navController = navController
                )
            }
        }

    }

}


@Composable
fun MyBottomNavigation(
    navController: NavController,
    currentDestination: NavDestination?,
    items: List<Screens>
) {


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

