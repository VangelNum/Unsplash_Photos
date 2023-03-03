package com.vangelnum.unsplash.feature_search.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vangelnum.unsplash.feature_search.data.dto.Result
import com.vangelnum.unsplash.feature_search.domain.repository.SearchRepository

class SearchPagingSource(
    private val repository: SearchRepository,
    private val query: String,

) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            if (query.isNotBlank()) {
                val page = params.key ?: 1
                val response = repository.getSearchPhotos(page, query, per_page = 10)
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (response.results.isEmpty()) null else page.plus(1),
                )
            } else {
                LoadResult.Error(Exception())
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
