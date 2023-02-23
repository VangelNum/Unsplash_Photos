package com.vangelnum.unsplash.feature_random.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.feature_random.domain.model.PhotosItemsRandom
import com.vangelnum.unsplash.feature_random.domain.repository.RandomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val repository: RandomRepository
) : ViewModel() {

    private val _randomState = MutableStateFlow<Resource<List<PhotosItemsRandom>>>(Resource.Loading())
    val randomState = _randomState.asStateFlow()


    init {
        getRandomPhotos(count = 30)
    }

    fun getRandomPhotos(count: Int) {
        viewModelScope.launch {
            repository.getRandomPhotos(count).collect {
                when (it) {
                    is Resource.Success -> {
                        _randomState.value = Resource.Success(it.data)
                    }

                    is Resource.Error -> {
                        _randomState.value = Resource.Error(it.message.toString())
                    }

                    is Resource.Loading -> {
                        _randomState.value = Resource.Loading()
                    }
                }
            }
        }
    }
}