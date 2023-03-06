package com.vangelnum.unsplash.feature_watch.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WatchPhotoViewModel : ViewModel() {
    private val _stateWidth = MutableStateFlow(0F)
    val stateWidth = _stateWidth.asStateFlow()

    private val _stateHeight = MutableStateFlow(0F)
    val stateHeight = _stateHeight.asStateFlow()

    fun triggerStates(height: Float, width: Float) {
        _stateHeight.value = height
        _stateWidth.value = width
    }
}
