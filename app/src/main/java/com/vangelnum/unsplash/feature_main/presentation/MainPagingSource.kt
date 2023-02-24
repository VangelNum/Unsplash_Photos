package com.vangelnum.unsplash.feature_main.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vangelnum.unsplash.feature_main.domain.model.PhotosItems
import com.vangelnum.unsplash.feature_main.domain.repository.MainRepository

class MainPagingSource(
    private val repository: MainRepository
) : PagingSource<Int, PhotosItems>() {
    override fun getRefreshKey(state: PagingState<Int, PhotosItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosItems> {
        return try {
            val page = params.key ?: 1
            val photoResponse = repository.getAllMainPhotos(page, 30, "latest")
            LoadResult.Page(
                data = photoResponse,
                prevKey = null,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}