package com.vangelnum.unsplash.feature_popular.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularScreen(
    viewModel: PopularViewModel = hiltViewModel(),
) {
    val photos = viewModel.pagerPopular.collectAsLazyPagingItems()

    val state = rememberSwipeRefreshState(
        isRefreshing = photos.loadState.refresh is LoadState.Loading,
    )

    SwipeRefresh(state = state, onRefresh = { photos.refresh() }) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(128.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            if (photos.loadState.refresh is LoadState.NotLoading) {
                items(photos) { photo ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        if (photo != null) {
                            SubcomposeAsyncImage(
                                model = photo.urls.full,
                                contentDescription = "photo",
                                contentScale = ContentScale.FillWidth,
                            ) {
                                when (painter.state) {
                                    is AsyncImagePainter.State.Loading -> {
                                        Box(
                                            modifier = Modifier
                                                .height(300.dp)
                                                .placeholder(
                                                    visible = true,
                                                    color = Color(photo.color.toColorInt()),
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
                    }

                }
            }
        }
    }

}