package com.vangelnum.unsplash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vangelnum.unsplash.presentation.navigation.Navigation
import com.vangelnum.unsplash.ui.theme.StackoverflowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StackoverflowTheme {
                Navigation()
            }
        }
    }
}
