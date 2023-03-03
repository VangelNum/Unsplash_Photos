package com.vangelnum.unsplash.feature_collections.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.core.presentation.navigation.Screens

@Composable
fun CollectionsScreen(navController: NavController) {

    val mobileBrandsItems = listOf(
        CollectionItems(R.drawable.asus, "Asus"),
        CollectionItems(R.drawable.google, "Google"),
        CollectionItems(R.drawable.htc, "HTC"),
        CollectionItems(R.drawable.huawei, "Huawei"),
        CollectionItems(R.drawable.iphone, "Iphone"),
        CollectionItems(R.drawable.lenovo, "Lenovo"),
        CollectionItems(R.drawable.oneplus, "OnePlus"),
        CollectionItems(R.drawable.samsung, "Samsung"),
        CollectionItems(R.drawable.xiaomi, "Xiaomi"),
        CollectionItems(R.drawable.zte, "ZTE")
    )

    val colorItems = listOf(
        ColorItems(Color.Blue, "blue"),
        ColorItems(Color.Red, "red"),
        ColorItems(Color.Green, "green"),
        ColorItems(Color.Yellow, "yellow"),
        ColorItems(Color.Magenta, "magenta"),
    )

    val lazyGridItems = listOf(
        CollectionItems(R.drawable.aabstract, "Abstract"),
        CollectionItems(R.drawable.animal, "Animal"),
        CollectionItems(R.drawable.anime2, "Anime"),
        CollectionItems(R.drawable.car, "Car"),
        CollectionItems(R.drawable.cartoon, "Cartoon"),
        CollectionItems(R.drawable.city, "City"),
        CollectionItems(R.drawable.colorful, "Colorful"),
        CollectionItems(R.drawable.horror, "Horror"),
        CollectionItems(R.drawable.flower, "Flower"),
        CollectionItems(R.drawable.food, "Food")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(id = (R.string.mobile_brends)),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
            ) {
                items(mobileBrandsItems) { photo ->
                    BrandCard(photo = photo, navController)
                }
            }
        }
        item {
            Text(
                text = stringResource(id = (R.string.colors)),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
            ) {
                items(colorItems) { color ->
                    CanvasCards(color, navController)
                }
            }
        }
        item {
            Text(
                text = stringResource(id = (R.string.categories)),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        item {
            VerticalGrid(rows = 2, items = lazyGridItems, navController = navController)
        }
    }
}


@Composable
fun VerticalGrid(rows: Int, items: List<CollectionItems>, navController: NavController) {
    val chunkedItems = items.chunked(rows)
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        for (rowItems in chunkedItems) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                for (item in rowItems) {
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.navigate(Screens.SearchCollectionScreen.withArgs(item.name))
                            }
                    ) {
                        Box(
                            modifier = Modifier.height(120.dp)
                        ) {
                            Image(
                                painter = painterResource(id = item.photoId),
                                contentDescription = "catogies",
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black
                                            ),
                                            startY = 10F
                                        )
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                Text(text = item.name, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BrandCard(photo: CollectionItems, navController: NavController) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .width(140.dp)
                .clickable {
                    navController.navigate(Screens.SearchCollectionScreen.withArgs(photo.name))
                },
        ) {
            Image(
                painter = painterResource(id = photo.photoId),
                contentDescription = "brand",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 10F
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = photo.name, color = Color.White)
            }
        }
    }

}

@Composable
fun CanvasCards(color: ColorItems, navController: NavController) {
    Canvas(modifier = Modifier
        .size(80.dp)
        .clickable {
            navController.navigate(Screens.SearchCollectionScreen.withArgs(color.name))
        }) {
        drawCircle(color.color)
    }
}