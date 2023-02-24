package com.vangelnum.unsplash.feature_popular.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vangelnum.unsplash.feature_popular.domain.repository.PopularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val repository: PopularRepository
) : ViewModel() {
    val pagerPopular = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            PopularPagingSource(repository)
        }
    ).flow.cachedIn(viewModelScope)
}