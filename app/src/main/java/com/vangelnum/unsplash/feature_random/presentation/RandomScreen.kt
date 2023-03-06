package com.vangelnum.unsplash.feature_random.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.core.presentation.navigationHost.Screens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun RandomScreen(
    navController: NavController,
    viewModel: RandomViewModel = hiltViewModel()
) {

    val randomStates = viewModel.randomState.collectAsState()
    when (randomStates.value) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error ${randomStates.value.message}")
            }
        }

        is Resource.Success -> {
            HorizontalPager(
                randomStates.value.data?.size ?: 0,
                contentPadding = PaddingValues(bottom = 16.dp, end = 32.dp, start = 32.dp),
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
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
                ) {
                    Card(
                        shape = MaterialTheme.shapes.extraLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f).clickable {

                                val encodedUrl = URLEncoder.encode(
                                    randomStates.value.data?.get(page)?.urls?.regular,
                                    StandardCharsets.UTF_8.toString()
                                )
                                navController.navigate(
                                    Screens.WatchPhotoScreen.withArgs(
                                        encodedUrl,
                                        randomStates.value.data?.get(page)?.id!!
                                    )
                                )
                            }
                    ) {
                        AsyncImage(
                            model = randomStates.value.data?.get(page)?.urls?.regular,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )

                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(1f),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Card(
                            shape = CircleShape,
                        ) {
                            SubcomposeAsyncImage(
                                model = randomStates.value.data?.get(page)?.user?.profile_image?.large,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight(0.35f)
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Text(
                            textAlign = TextAlign.Center,
                            text = randomStates.value.data?.get(page)?.user?.username?.uppercase()
                                .toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "favourite",
                            )
                            Text(
                                text = randomStates.value.data?.get(page)?.likes.toString(),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            textAlign = TextAlign.Center,
                            text = randomStates.value.data?.get(page)?.created_at.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = randomStates.value.data?.get(page)?.user?.location.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

        }
    }

}