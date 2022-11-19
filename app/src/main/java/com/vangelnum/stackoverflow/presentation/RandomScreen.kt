package com.vangelnum.stackoverflow.presentation.navigation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.LazyVerticalGrid.items
import com.vangelnum.stackoverflow.R
import com.vangelnum.stackoverflow.room.PhotoItem
import com.vangelnum.stackoverflow.viewmodel.ViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RandomPhotos(
    viewModel: ViewModel,
    navController: NavController,
    itemsFavouritePhotos: List<PhotoItem>,
) {
    val photos = viewModel.pagerRandom.collectAsLazyPagingItems()
    if (photos.itemCount == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Blue)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(photos) { item ->
            if (item != null) {
                SubcomposeAsyncImage(
                    contentScale = ContentScale.Crop,
                    model = item.urls.full,
                    loading = {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                color = Color.Green
                            )
                        }
                    },
                    contentDescription = "images",
                    modifier = Modifier
                        .height(300.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .clickable {
                            val encodedUrl =
                                URLEncoder.encode(item.urls.full,
                                    StandardCharsets.UTF_8.toString())
                            navController.navigate(Screens.WatchPhotoScreen.withArgs(encodedUrl))
                        }
                )
                var tint by remember {
                    mutableStateOf(Color.White)
                }
                for (items in itemsFavouritePhotos) {
                    if (items.urlPhoto == item.urls.full) {
                        tint = Color.Red
                    }
                    if (tint == Color.Red) break
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .height(300.dp)
                    .padding(all = 10.dp),
                    contentAlignment = Alignment.BottomEnd) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
                        contentDescription = "fav",
                        tint = tint
                    )
                }
            }
        }
    }
}