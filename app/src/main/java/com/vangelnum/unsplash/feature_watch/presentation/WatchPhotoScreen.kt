package com.vangelnum.unsplash.feature_watch.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.feature_favourite.domain.model.FavouriteItem
import com.vangelnum.unsplash.feature_favourite.presentation.FavouriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchPhotoScreen(
    url: String?,
    id: String?,
    openBottomSheet: Boolean,
    bottomSheetState: SheetState,
    watchPhotoViewModel: WatchPhotoViewModel,
    onDismiss: () -> Unit,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val photoInFavourite = remember {
        mutableStateOf(false)
    }

    val state = viewModel.photosState.collectAsState()
    photoInFavourite.value = url?.let { state.value.data?.toString()?.contains(it) } == true


    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (id != null && url != null) {
                    viewModel.addPhoto(FavouriteItem(id, url))
                }
            },
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            loading = {
                watchPhotoViewModel.triggerStates(0F, 0F)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            success = {
                val height = painter.intrinsicSize.height
                val width = painter.intrinsicSize.width
                watchPhotoViewModel.triggerStates(height, width)
                SubcomposeAsyncImageContent()
            }
        )
    }
    val context = LocalContext.current
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = bottomSheetState,
        ) {
            ListItem(modifier = Modifier.clickable {
                if (id != null && url != null) {
                    if (photoInFavourite.value) viewModel.deletePhoto(
                        FavouriteItem(
                            id,
                            url
                        )
                    ) else viewModel.addPhoto(
                        FavouriteItem(id, url)
                    )
                }
            }, leadingContent = {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "favorite",
                    tint = if (photoInFavourite.value) Color.Red else Color.White
                )
            }, headlineText = {
                if (photoInFavourite.value) Text(text = stringResource(id = R.string.delete_from_favourite)) else Text(
                    stringResource(id = R.string.add_to_favourite)
                )
            })
            ListItem(
                modifier = Modifier.clickable {
                    if (url != null) {
                        watchPhotoViewModel.download(url = url, context)
                    }
                },
                headlineText = { Text(stringResource(id = R.string.download)) },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_download_24),
                        contentDescription = "download"
                    )
                },
            )
            ListItem(leadingContent = {
                Icon(imageVector = Icons.Outlined.Share, contentDescription = "share")
            }, modifier = Modifier.clickable {
                if (url != null) {
                    watchPhotoViewModel.share(url, context)
                }
            }, headlineText = { Text(stringResource(id = R.string.share)) })
            ListItem(modifier = Modifier.clickable {
                if (url != null) {
                    watchPhotoViewModel.setImage(url, context)
                }
            }, leadingContent = {
                Icon(
                    painterResource(id = R.drawable.baseline_collections_24),
                    contentDescription = "set"
                )
            }, headlineText = { Text(stringResource(id = R.string.set)) })
        }
    }
}
