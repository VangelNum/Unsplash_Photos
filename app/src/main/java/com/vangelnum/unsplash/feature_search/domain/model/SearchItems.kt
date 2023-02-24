package com.vangelnum.unsplash.feature_search.domain.model

import com.vangelnum.unsplash.feature_search.data.dto.Result

data class SearchItems (
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)