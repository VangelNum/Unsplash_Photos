package com.vangelnum.stackoverflow.presentation

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.stackoverflow.R
import com.vangelnum.stackoverflow.room.PhotoItem
import com.vangelnum.stackoverflow.ui.theme.myblackcolor
import com.vangelnum.stackoverflow.viewmodel.ViewModel


@Composable
fun WatchPhoto(url: String?, viewModel: ViewModel) {

    var currentStateFavourite by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.padding(15.dp)
        ) {
            SubcomposeAsyncImage(
                model = url,
                contentDescription = "image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp, top = 15.dp, start = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly) {


                Card(
                    backgroundColor = MaterialTheme.colors.myblackcolor,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            share(url = url!!, context = context)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_share_24),
                        contentDescription = "share",
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Card(
                    backgroundColor = MaterialTheme.colors.myblackcolor,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            download(url = url!!, context = context)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_download_24),
                        contentDescription = "download",
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Card(
                    backgroundColor = MaterialTheme.colors.myblackcolor,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            currentStateFavourite = true
                            viewModel.addPhoto(PhotoItem(idPhoto = 0, urlPhoto = url!!))
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
                        contentDescription = "favourite",
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}


private fun download(url: String, context: Context) {
    val request = DownloadManager.Request(Uri.parse(url))
    request.setDescription("Downloading")
    request.setMimeType("image/*")
    request.setTitle("File")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
        "photo.png")
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
