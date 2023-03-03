package com.vangelnum.unsplash.feature_collections.presentation

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.core.lazy_grid_extension.items
import com.vangelnum.unsplash.core.presentation.navigation.Screens
import com.vangelnum.unsplash.feature_search.presentation.SearchViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionSearchScreen(
    query: String?,
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val pagerState = searchViewModel.pagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        if (query != null) {
            searchViewModel.queryFlow.value = query
        }
    }

    val state = rememberSwipeRefreshState(
        isRefreshing = pagerState.loadState.refresh is LoadState.Loading,
    )

    SwipeRefresh(state = state, onRefresh = { pagerState.refresh() }) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(128.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(pagerState) { photo ->
                if (photo != null) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.clickable {
                            val encodedUrl = URLEncoder.encode(
                                photo.urls.full,
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
                        SubcomposeAsyncImage(
                            model = photo.urls.regular,
                            contentDescription = "photo",
                            contentScale = ContentScale.FillWidth,
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
                    Text(text = stringResource(id = R.string.error))
                }
            }
        }
    }

}