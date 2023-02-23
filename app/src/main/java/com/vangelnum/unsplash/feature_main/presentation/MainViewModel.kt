package com.vangelnum.unsplash.feature_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vangelnum.unsplash.feature_main.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val pager = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            MainPagingSource(repository)
        }
    ).flow.cachedIn(viewModelScope)

}