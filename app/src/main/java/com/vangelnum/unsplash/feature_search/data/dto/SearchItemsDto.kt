package com.vangelnum.unsplash.feature_search.data.dto

data class SearchItemsDto(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)