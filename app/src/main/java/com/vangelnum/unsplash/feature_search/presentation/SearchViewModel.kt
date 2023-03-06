package com.vangelnum.unsplash.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vangelnum.unsplash.feature_search.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    val queryFlow = MutableStateFlow("")

    val pagingDataFlow = queryFlow.flatMapLatest { query ->
        Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchPagingSource(repository, query)
        }.flow.cachedIn(viewModelScope)
    }
}