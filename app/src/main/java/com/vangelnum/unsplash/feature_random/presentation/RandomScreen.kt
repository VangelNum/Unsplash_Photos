package com.vangelnum.unsplash.feature_random.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.feature_random.domain.model.PhotosItemsRandom


@Composable
fun RandomScreen(
    viewModel: RandomViewModel = hiltViewModel()
) {

    val randomStates = viewModel.randomState.collectAsState()

    val state = rememberSwipeRefreshState(
        isRefreshing = randomStates.value is Resource.Loading
    )

    SwipeRefresh(state = state, onRefresh = { viewModel.getRandomPhotos(30) }) {
        RandomItems(photos = randomStates.value.data)
        if (randomStates.value.message?.isNotEmpty() == true) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = randomStates.value.message.toString())
                Button(onClick = {
                    viewModel.getRandomPhotos(30)
                }) {
                    Text(text = stringResource(id = R.string.reload))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RandomItems(
    photos: List<PhotosItemsRandom>?
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(128.dp),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        if (photos != null) {
            items(photos) { photo ->
                Card(
                    shape = RoundedCornerShape(16.dp)
                ) {
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


//@Composable
//fun RandomPhotos(
//    viewModel: ViewModel,
//    navController: NavController,
//    itemsFavouritePhotos: List<PhotoItem>,
//) {
//    val photos = viewModel.pagerRandom.collectAsLazyPagingItems()
//    if (photos.itemCount == 0) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator(color = Color.Blue)
//        }
//    }
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        contentPadding = PaddingValues(8.dp)
//    ) {
//        items(photos) { item ->
//            if (item != null) {
//                SubcomposeAsyncImage(
//                    contentScale = ContentScale.Crop,
//                    model = item.urls.full,
//                    loading = {
//                        Box(contentAlignment = Alignment.Center) {
//                            CircularProgressIndicator(
//                                color = Color.Green
//                            )
//                        }
//                    },
//                    contentDescription = "images",
//                    modifier = Modifier
//                        .height(300.dp)
//                        .clip(RoundedCornerShape(15.dp))
//                        .clickable {
//                            val encodedUrl =
//                                URLEncoder.encode(item.urls.full,
//                                    StandardCharsets.UTF_8.toString())
//                            navController.navigate(Screens.WatchPhotoScreen.withArgs(encodedUrl))
//                        }
//                )
//                var tint by remember {
//                    mutableStateOf(Color.White)
//                }
//                for (items in itemsFavouritePhotos) {
//                    if (items.urlPhoto == item.urls.full) {
//                        tint = Color.Red
//                    }
//                    if (tint == Color.Red) break
//                }
//                Box(modifier = Modifier
//                    .fillMaxSize()
//                    .height(300.dp)
//                    .padding(all = 10.dp),
//                    contentAlignment = Alignment.BottomEnd) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
//                        contentDescription = "fav",
//                        tint = tint
//                    )
//                }
//            }
//        }
//    }
//}