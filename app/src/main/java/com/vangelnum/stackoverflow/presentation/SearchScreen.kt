package com.vangelnum.stackoverflow.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.stackoverflow.dataclass.forsearchphotos.Result
import com.vangelnum.stackoverflow.network.ApiInterface
import com.vangelnum.stackoverflow.presentation.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchScreen(query: String) {

    var itss by remember {
        mutableStateOf(listOf<Result>())
    }
    GlobalScope.launch (Dispatchers.IO){
        itss =  ApiInterface.create().getSearchPhotos(1, query = query).results
    }


    if (itss.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Nothing found", textAlign = TextAlign.Center)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(itss) { item ->
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
//                        val encodedUrl =
//                            URLEncoder.encode(item.urlPhoto, StandardCharsets.UTF_8.toString())
//                        navController.navigate(Screens.WatchPhotoScreen.withArgs(encodedUrl))
                    }
            )
        }
    }

}