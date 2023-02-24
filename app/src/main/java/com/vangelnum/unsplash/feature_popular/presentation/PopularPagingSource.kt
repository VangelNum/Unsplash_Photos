package com.vangelnum.unsplash.feature_popular.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vangelnum.unsplash.feature_popular.domain.model.PopularItems
import com.vangelnum.unsplash.feature_popular.domain.repository.PopularRepository

class PopularPagingSource(
    private val repository: PopularRepository
) : PagingSource<Int, PopularItems>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularItems> {
        return try {
            val page = params.key ?: 1
            val photoResponse = repository.getAllPopularPhotos(page, 30, "popular")
            LoadResult.Page(
                data = photoResponse,
                prevKey = null,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PopularItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}