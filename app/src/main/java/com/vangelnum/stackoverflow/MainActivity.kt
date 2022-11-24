package com.vangelnum.stackoverflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.vangelnum.stackoverflow.presentation.navigation.Navigation
import com.vangelnum.stackoverflow.ui.theme.StackoverflowTheme
import com.vangelnum.stackoverflow.viewmodel.ViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StackoverflowTheme {
                val vm by viewModels<ViewModel>()
                Navigation(viewModel = vm)
            }
        }
    }
}
