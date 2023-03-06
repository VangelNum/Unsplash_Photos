package com.vangelnum.unsplash.core.presentation.navigation

import FavouriteScreen
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.vangelnum.unsplash.core.presentation.DrawerBody
import com.vangelnum.unsplash.core.presentation.DrawerHeader
import com.vangelnum.unsplash.core.presentation.NavigationScreen
import com.vangelnum.unsplash.feature_collections.presentation.CollectionSearchScreen
import com.vangelnum.unsplash.feature_collections.presentation.CollectionsScreen
import com.vangelnum.unsplash.feature_contact.presentation.ContactScreen
import com.vangelnum.unsplash.feature_latest.presentation.MainScreen
import com.vangelnum.unsplash.feature_popular.presentation.PopularScreen
import com.vangelnum.unsplash.feature_random.presentation.RandomScreen
import com.vangelnum.unsplash.feature_search.presentation.SearchScreen
import com.vangelnum.unsplash.feature_watch.presentation.WatchPhotoScreen
import com.vangelnum.unsplash.feature_watch.presentation.WatchPhotoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    watchViewModel: WatchPhotoViewModel = hiltViewModel()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val itemsScreens = listOf(
        Screens.NavigationScreen,
        Screens.CollectionsScreen,
        Screens.FavoriteScreen
    )

    var openBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberSheetState(skipHalfExpanded = true)

    val width = watchViewModel.stateWidth.collectAsState()
    val height = watchViewModel.stateHeight.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (drawerState.isOpen) {
                BackHandler() {
                    scope.launch {
                        drawerState.close()
                    }
                }
            }
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(navController = navController,drawerState)
            }
        },
        content = {
            Scaffold(
                topBar = {
                    val currentRoute = navController.currentDestination?.route
                    val itemsRoutes = itemsScreens.map {
                        it.route
                    }
                    if (currentRoute != null && currentRoute !in itemsRoutes) {
                        CenterAlignedTopAppBar(
                            title = {
                                if (currentRoute == Screens.WatchPhotoScreen.route + "/{url}" + "/{id}") {
                                    val widthPhoto = width.value.toInt().toString()
                                    val heightPhoto = height.value.toInt().toString()
                                    AnimatedVisibility(visible = widthPhoto != "0" && heightPhoto != "0",
                                        enter =
                                        expandHorizontally(
                                            tween(400, easing = LinearEasing)
                                        ) {
                                            it
                                        } + fadeIn(tween(200))
                                    ) {
                                        Text(
                                            text = "$widthPhoto x $heightPhoto",
                                            style = MaterialTheme.typography.titleMedium,
                                            maxLines = 1
                                        )
                                    }
                                } else {
                                    Text(
                                        text = currentDestination?.route.toString(),
                                        maxLines = 1,
                                        style = MaterialTheme.typography.titleMedium,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                                }
                            },
                            actions = {
                                if (currentRoute == Screens.WatchPhotoScreen.route + "/{url}" + "/{id}") {
                                    IconButton(onClick = {
                                        openBottomSheet = true
                                    }) {
                                        Icon(
                                            imageVector = Icons.Outlined.MoreVert,
                                            contentDescription = "more"
                                        )
                                    }
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
                                    scope.launch {
                                        drawerState.open()
                                    }
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
                        RandomScreen(navController = navController)
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
                        WatchPhotoScreen(
                            entry.arguments?.getString("url"),
                            entry.arguments?.getString("id"),
                            openBottomSheet,
                            bottomSheetState,
                            watchViewModel,
                            onDismiss = { openBottomSheet = false },
                        )
                    }
                    composable(route = Screens.PopularScreen.route) {
                        PopularScreen(navController = navController)
                    }
                    composable(
                        route = Screens.SearchScreen.route
                    ) {
                        SearchScreen(navController = navController)
                    }
                    composable(route = Screens.NavigationScreen.route) {
                        NavigationScreen(navController = navController)
                    }
                    composable(route = Screens.CollectionsScreen.route) {
                        CollectionsScreen(navController = navController)
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
                    composable(route = Screens.ContactScreen.route) {
                        ContactScreen()
                    }
                }

            }
        }
    )



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

