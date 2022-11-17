package com.vangelnum.stackoverflow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.LazyVerticalGrid.items
import com.vangelnum.stackoverflow.ui.theme.StackoverflowTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StackoverflowTheme {
                val vm by viewModels<ViewModel>()
                LibraryImage(viewModel = vm)
            }
        }
    }
}


@Composable
fun LibraryImage(viewModel: ViewModel) {

    val photos = viewModel.pager.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        Log.d("photos", photos.toString())
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
                )
            }
        }
    }
}

