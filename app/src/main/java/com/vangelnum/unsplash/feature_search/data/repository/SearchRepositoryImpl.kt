package com.vangelnum.unsplash.feature_search.data.repository

import com.vangelnum.unsplash.feature_search.data.api.ApiInterfaceSearch
import com.vangelnum.unsplash.feature_search.data.mapper.toNormalSearch
import com.vangelnum.unsplash.feature_search.domain.model.SearchItems
import com.vangelnum.unsplash.feature_search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: ApiInterfaceSearch
) : SearchRepository {
    override suspend fun getSearchPhotos(page: Int, query: String, per_page: Int): SearchItems {
        return api.getSearchPhotos(page, query, per_page).toNormalSearch()
    }

}