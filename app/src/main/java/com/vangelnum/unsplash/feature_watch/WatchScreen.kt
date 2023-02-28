package com.vangelnum.unsplash.feature_watch

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.vangelnum.unsplash.feature_favourite.domain.model.FavouriteItem
import com.vangelnum.unsplash.feature_favourite.presentation.FavouriteViewModel


@Composable
fun WatchPhoto(
    url: String?,
    id: String?,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                viewModel.addPhoto(FavouriteItem(id!!, url!!))
            },
        shape = MaterialTheme.shapes.large,
    ) {
        AsyncImage(
            model = url,
            contentDescription = "photo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


private fun download(url: String, context: Context) {
    val request = DownloadManager.Request(Uri.parse(url))
    request.setDescription("Downloading")
    request.setMimeType("image/*")
    request.setTitle("File")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS,
        "photo.png"
    )
    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
    manager!!.enqueue(request)
}

private fun share(url: String, context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/*"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}
