package com.vangelnum.unsplash.feature_contact.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vangelnum.unsplash.R

@Composable
fun ContactScreen(contactViewModel: ContactViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(id = R.string.gmail_vangelnum),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 24.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                   contactViewModel.emailSend(context)
                }
        )
        Text(
            text = stringResource(id = R.string.vk_com_vangelnum),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 24.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                   contactViewModel.goToMyVk(context)
                }
        )
    }
}

