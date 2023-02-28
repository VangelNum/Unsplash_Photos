package com.vangelnum.unsplash.feature_random.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.vangelnum.unsplash.core.common.Resource
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun RandomScreen(
    viewModel: RandomViewModel = hiltViewModel()
) {

    val randomStates = viewModel.randomState.collectAsState()
    if (randomStates.value is Resource.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    HorizontalPager(
        randomStates.value.data?.size ?: 0,
        contentPadding = PaddingValues(start = 32.dp, end = 32.dp, bottom = 16.dp),
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Card(
            Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxSize(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            AsyncImage(
                model = randomStates.value.data?.get(page)?.urls?.regular,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}