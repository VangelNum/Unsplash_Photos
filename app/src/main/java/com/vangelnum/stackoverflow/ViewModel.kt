package com.vangelnum.stackoverflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vangelnum.stackoverflow.pagination.PageSource


class ViewModel : ViewModel() {
    val pager = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            PageSource()
        }
    ).flow.cachedIn(viewModelScope)
}