package com.vangelnum.stackoverflow.dataclass.forsearchphotos

data class SearchItems(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)