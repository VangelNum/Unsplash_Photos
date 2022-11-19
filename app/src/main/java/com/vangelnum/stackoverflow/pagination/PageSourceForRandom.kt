package com.vangelnum.stackoverflow.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vangelnum.stackoverflow.dataclass.forlistphotos.PhotosItem
import com.vangelnum.stackoverflow.network.ApiInterface

class PageSourceForRandom: PagingSource<Int, PhotosItem>(){
    override fun getRefreshKey(state: PagingState<Int, PhotosItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosItem> {
        return try {
            val page = params.key ?: 1
            val photoResponse = ApiInterface.create().getRandomPhotos(count = 30)
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