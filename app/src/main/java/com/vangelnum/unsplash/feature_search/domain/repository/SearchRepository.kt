package com.vangelnum.unsplash.feature_search.domain.repository

import com.vangelnum.unsplash.feature_search.domain.model.SearchItems

interface SearchRepository {
    suspend fun getSearchPhotos(page: Int, query: String, per_page: Int): SearchItems

}

