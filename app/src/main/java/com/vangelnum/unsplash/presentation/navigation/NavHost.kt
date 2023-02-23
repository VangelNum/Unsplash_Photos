package com.vangelnum.unsplash.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.vangelnum.unsplash.feature_random.presentation.RandomScreen
import com.vangelnum.unsplash.presentation.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    val items = listOf(
        Screens.MainScreen,
        Screens.RandomScreen,
        Screens.PopularScreen,
        Screens.FavoriteScreen
    )

    //val itemsFavouritePhotos: List<PhotoItem> = viewModel.readAllData.observeAsState(listOf()).value

    var query by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }
            var visibleSearchBar by remember {
                mutableStateOf(false)
            }
            var visiblecurrentSearch by remember {
                mutableStateOf(true)
            }

            var searchtext by remember {
                mutableStateOf(false)
            }
            val state = remember { mutableStateOf(TextFieldValue("")) }
            TopAppBar(
                actions = {
                    LaunchedEffect(visibleSearchBar) {
                        if (visibleSearchBar) {
                            focusRequester.requestFocus()
                            keyboardController?.show()
                        }
                    }
                    AnimatedVisibility(visible = visiblecurrentSearch) {
                        IconButton(onClick = {
                            visiblecurrentSearch = false
                            visibleSearchBar = true
                            searchtext = true

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_outline_search_24),
                                contentDescription = "search"
                            )
                        }

                    }
                    AnimatedVisibility(
                        visible = visibleSearchBar,

                        ) {
                        TextField(
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .fillMaxWidth()
                                .padding(end = 10.dp)
                                .scale(scaleX = 1F, scaleY = 0.9F),
                            value = state.value,
                            onValueChange = { value ->
                                state.value = value
                            },
                            enabled = true,
                            shape = RoundedCornerShape(25.dp),

                            textStyle = TextStyle(color = Color.White),
                            placeholder = {
                                Text(
                                    text = "Search",
                                    fontSize = 14.sp,
                                )
                            },

                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (state.value.text != "") {
                                        query = state.value.text
                                        navController.navigate(Screens.SearchScreen.route)
                                        keyboardController?.hide()
                                    }
                                }
                            ),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_outline_search_24),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        state.value = TextFieldValue("")
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_round_close_24),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }

                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.Black,
                                leadingIconColor = Color.White,
                                trailingIconColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Black,
                            )
                        )
                    }


                },
                title = {

                },
                navigationIcon = {
                    if (!searchtext) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                                contentDescription = "menu"
                            )

                        }
                    } else {
                        IconButton(onClick = {
                            visibleSearchBar = false
                            visiblecurrentSearch = true
                            searchtext = false
                            keyboardController?.hide()
                            navController.navigate(Screens.MainScreen.route)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                contentDescription = "menu"
                            )

                        }
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
//                MainScreen(viewModel = viewModel,
//                    navController = navController,
//                    itemsFavouritePhotos = itemsFavouritePhotos)
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
//                PopularScreen(viewModel = viewModel,
//                    navController = navController,
//                    itemsFavouritePhotos = itemsFavouritePhotos)
            }
            composable(
                route = Screens.SearchScreen.route
            ) {
                SearchScreen(query = query)
            }

        }
    }


}