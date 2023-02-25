package com.vangelnum.unsplash.feature_favourite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.feature_favourite.domain.model.FavouriteItem
import com.vangelnum.unsplash.feature_favourite.domain.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: FavouriteRepository
) : ViewModel() {

    private val _photosState = MutableStateFlow<Resource<List<FavouriteItem>>>(Resource.Loading())
    val photosState: StateFlow<Resource<List<FavouriteItem>>> = _photosState.asStateFlow()


    init {
        getPhotos()
    }

    fun getPhotos() {
        viewModelScope.launch {
            repository.getAllPhotos().collect {
                _photosState.value = it
            }
        }
    }

    fun addPhoto(photo: FavouriteItem) {
        viewModelScope.launch {
            repository.addPhoto(photo)
        }
    }

    fun deletePhoto(photo: FavouriteItem) {
        viewModelScope.launch {
            repository.deletePhoto(photo)
        }
    }
}