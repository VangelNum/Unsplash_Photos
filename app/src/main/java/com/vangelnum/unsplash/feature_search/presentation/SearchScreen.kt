package com.vangelnum.unsplash.feature_search.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.core.lazy_grid_extension.items
import com.vangelnum.unsplash.core.presentation.navigation.Screens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun SearchScreen(
    viewModelSearch: SearchViewModel = hiltViewModel(),
    navController: NavController
) {

    val pagerState = viewModelSearch.pagingDataFlow.collectAsLazyPagingItems()


    if (pagerState.loadState.refresh is LoadState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(128.dp),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            AutoFillSearch(viewModelSearch)
        }
        items(pagerState) { photo ->
            if (photo != null) {
                Card(shape = RoundedCornerShape(16.dp)) {
                    SubcomposeAsyncImage(
                        model = photo.urls.regular,
                        contentDescription = "photo",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.clickable {
                            val encodedUrl = URLEncoder.encode(
                                photo.urls.regular,
                                StandardCharsets.UTF_8.toString()
                            )
                            navController.navigate(
                                Screens.WatchPhotoScreen.withArgs(
                                    encodedUrl,
                                    photo.id
                                )
                            )
                        }
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .height(300.dp)
                                        .fillMaxWidth()
                                        .placeholder(
                                            visible = true,
                                            color = Color(photo.color.toColorInt()),
                                            shape = RoundedCornerShape(4.dp),
                                            highlight = PlaceholderHighlight.shimmer(
                                                highlightColor = Color.White,
                                            ),
                                        )
                                )
                            }

                            is AsyncImagePainter.State.Error -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_error_outline_24),
                                        contentDescription = "error icon"
                                    )
                                }
                            }

                            is AsyncImagePainter.State.Success -> {
                                SubcomposeAsyncImageContent()
                            }

                            else -> Unit
                        }

                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(id = R.string.enter_something))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AutoFillSearch(viewModelSearch: SearchViewModel) {
    val options = listOf(
        "Apple",
        "Banana",
        "Car",
        "City",
        "Girl",
        "Movie",
        "Space",
        "Sport",
        "Forest",
        "Woman",
        "Money"
    )
    var expanded by rememberSaveable {
        mutableStateOf(true)
    }
    var selectedOptionText by rememberSaveable {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }

    var itWasShow by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        if (!itWasShow) {
            focusRequester.requestFocus()
            itWasShow = true
        }
    }
    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedOptionText,
            onValueChange = {
                selectedOptionText = it
            },
            label = { Text(stringResource(id = R.string.search)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .focusRequester(focusRequester),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                viewModelSearch.queryFlow.value = selectedOptionText
            })

        )
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(modifier = Modifier.fillMaxWidth(), trailingIcon = {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "search")
                },
                    onClick = {
                        selectedOptionText = selectionOption
                        viewModelSearch.queryFlow.value = selectedOptionText
                        expanded = false
                    },
                    text = { Text(text = selectionOption) }
                )
            }
        }
    }
}

