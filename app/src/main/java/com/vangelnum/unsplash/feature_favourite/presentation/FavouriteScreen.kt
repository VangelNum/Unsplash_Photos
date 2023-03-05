import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.core.presentation.navigation.Screens
import com.vangelnum.unsplash.feature_favourite.domain.model.FavouriteItem
import com.vangelnum.unsplash.feature_favourite.presentation.FavouriteViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavouriteViewModel = hiltViewModel()
) {

    val resource = viewModel.photosState.collectAsState()

    when (resource.value) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            FavouritePhotosLazyGrid(
                viewModel = viewModel,
                allFavouritePhotos = resource.value.data,
                navController = navController
            )
        }

        is Resource.Error -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = resource.value.message.toString())
                Button(onClick = { viewModel.getPhotos() }) {
                    Text(text = stringResource(id = R.string.reload))
                }
            }
        }
    }

}

@Composable
fun FavouritePhotosLazyGrid(
    viewModel: FavouriteViewModel,
    allFavouritePhotos: List<FavouriteItem>?,
    navController: NavController
) {

    if (allFavouritePhotos?.isEmpty() == false) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
        ) {
            items(allFavouritePhotos) { photo ->
                Card(
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.fillMaxWidth()
                    .height(350.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = photo.urlPhoto,
                        contentDescription = "photo",
                        modifier = Modifier
                            .clickable {
                                val encodedUrl =
                                    URLEncoder.encode(
                                        photo.urlPhoto,
                                        StandardCharsets.UTF_8.toString()
                                    )
                                navController.navigate(
                                    Screens.WatchPhotoScreen.withArgs(
                                        encodedUrl,
                                        photo.idPhoto
                                    )
                                )
                            },
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        },
                        success = {
                            SubcomposeAsyncImageContent()
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                                IconButton(onClick = {
                                    viewModel.deletePhoto(FavouriteItem(photo.idPhoto,photo.urlPhoto))
                                }) {
                                    Icon(tint = Color.White,imageVector = Icons.Filled.Delete, contentDescription = "delete")
                                }
                            }
                        }
                    )
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = R.string.favourite_empty))
        }
    }

}