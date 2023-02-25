package com.vangelnum.unsplash.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.core.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(
    navController: NavController
) {
    var textValue by rememberSaveable {
        mutableStateOf("")
    }

    val items = listOf(
        NavigationItem(
            text = "Latest",
            image = R.drawable.exampleimage,
            routeMain = Screens.MainScreen.route
        ),
        NavigationItem(
            text = "Popular",
            image = R.drawable.exampleimage2,
            routeMain = Screens.PopularScreen.route
        ),
        NavigationItem(
            text = "Random",
            image = R.drawable.exampleimage3,
            routeMain = Screens.RandomScreen.route
        )
    )

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = textValue, onValueChange = {
                textValue = it
            }, modifier = Modifier.fillMaxWidth(), placeholder = {
                Text(text = "Search wallpapers")
            }, trailingIcon = {
                IconButton(onClick = { textValue = "" }) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "search")
                }
            }, singleLine = true
        )
        items.forEach { res ->
            Text(
                text = res.text,
                style = MaterialTheme.typography.titleLarge
            )
            Card(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.clickable {
                    navController.navigate(res.routeMain)
                }
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    painter = painterResource(id = res.image),
                    contentDescription = "example",
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

