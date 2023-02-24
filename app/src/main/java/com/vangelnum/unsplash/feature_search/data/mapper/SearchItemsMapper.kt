package com.vangelnum.unsplash.feature_search.data.mapper

import com.vangelnum.unsplash.feature_search.data.dto.SearchItemsDto
import com.vangelnum.unsplash.feature_search.domain.model.SearchItems

fun SearchItemsDto.toNormalSearch() : SearchItems {
    return SearchItems(
        results = results,
        total = total,
        total_pages = total_pages
    )
}