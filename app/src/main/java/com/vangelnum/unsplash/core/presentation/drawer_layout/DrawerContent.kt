package com.vangelnum.unsplash.core.presentation.drawer_layout

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.vangelnum.unsplash.core.presentation.navigationHost.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(16.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onTertiary),
        contentAlignment = Alignment.Center,
        content = {
            Icon(
                Icons.Filled.Person,
                contentDescription = "Person icon",
                modifier = Modifier.size(40.dp)
            )
        }
    )
    Text(
        text = "VangelNum Wallpaper",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
    )
    Divider()

}

@Composable
fun DrawerBody(navController: NavController, drawerState: DrawerState) {

    val items = listOf(
        DrawerItems.Contacts,
        DrawerItems.Share,
        DrawerItems.SoundBoard,
        DrawerItems.DrumPad,
        DrawerItems.Wallpaper,
        DrawerItems.Pizza
    )
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LazyColumn {
        itemsIndexed(items) { index, item ->
            ListItem(modifier = Modifier.clickable {
                onEvent(item, navController, context, scope,drawerState)
            }, headlineText = {
                Text(text = item.title)
            }, leadingContent = {
                if (index > 1) {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                    )
                }
            })
        }
    }
}


fun onEvent(
    title: DrawerItems,
    navController: NavController,
    context: Context,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    when (title) {
        is DrawerItems.Share -> {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, "Спасибо за то, что поделился приложением! ❤")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "https://github.com/VangelNum/Unsplash_Photos"
                )
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(sendIntent, "Share..."))
        }

        is DrawerItems.SoundBoard -> {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=com.zxcursedsoundboard.apk")
            context.startActivity(intent)
        }

        is DrawerItems.DrumPad -> {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=com.vangelnum.drumpad")
            context.startActivity(intent)
        }

        is DrawerItems.Contacts -> {
            scope.launch {
                drawerState.close()
            }
            navController.navigate(Screens.ContactScreen.route, navOptions {
                launchSingleTop = true
            })
        }
        is DrawerItems.Pizza ->{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=com.vangelnum.pizza")
            context.startActivity(intent)
        }
        is DrawerItems.Wallpaper ->{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=com.zxcursed.wallpaper")
            context.startActivity(intent)
        }
    }
}
