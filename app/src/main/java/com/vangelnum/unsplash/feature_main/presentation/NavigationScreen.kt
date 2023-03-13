package com.vangelnum.unsplash.feature_main.presentation

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vangelnum.unsplash.R
import com.vangelnum.unsplash.core.presentation.navigationHost.Screens
import com.vangelnum.unsplash.feature_main.data.NavigationItem

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
            text = stringResource(id = R.string.latest),
            image = R.drawable.exampleimage,
            routeMain = Screens.MainScreen.route
        ),
        NavigationItem(
            text = stringResource(id = R.string.popular),
            image = R.drawable.exampleimage2,
            routeMain = Screens.PopularScreen.route
        ),
        NavigationItem(
            text = stringResource(id = R.string.random),
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
            enabled = false,
            value = textValue, onValueChange = {
                textValue = it
            }, modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screens.SearchScreen.route)
                },
            placeholder = {
                Text(text = stringResource(id = R.string.search_wallpapers))
            }, trailingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "search")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant)
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

