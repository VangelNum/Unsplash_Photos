package com.vangelnum.unsplash.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vangelnum.unsplash.core.data.dtoMainAndRandom.PhotosItemDto
import com.vangelnum.unsplash.network.ApiInterface

class PageSource : PagingSource<Int, PhotosItemDto>() {
    override fun getRefreshKey(state: PagingState<Int, PhotosItemDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosItemDto> {
        return try {
            val page = params.key ?: 1
            val photoResponse = ApiInterface.create().getPhotos(page,"latest")
            LoadResult.Page(
                data = photoResponse.body()!!,
                prevKey = null,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}