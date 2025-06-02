package com.fajarxfce.feature.pos.data.paging

import com.fajarxfce.core.data.BasePagingSource
import com.fajarxfce.feature.pos.data.mapper.toDomainList
import com.fajarxfce.feature.pos.data.model.CategoryDataItem
import com.fajarxfce.feature.pos.data.source.PosApi
import com.fajarxfce.feature.pos.domain.model.Category
import com.fajarxfce.feature.pos.domain.params.GetCategoryByQueryParams
import com.fajarxfce.feature.pos.domain.params.toQueryMap

internal class CategoryPagingSource(
    private val posApi: PosApi,
    private val initialParams: GetCategoryByQueryParams
) : BasePagingSource<Category, CategoryDataItem>() {
    override suspend fun fetchData(
        page: Int,
        pageSize: Int,
    ): List<CategoryDataItem> {
        val paramsForApiCall = initialParams.copy(page = page)
        val response = posApi.getCateoryByQuery(paramsForApiCall.toQueryMap())
        return response.data
            ?.data
            ?.filterNotNull()
            ?: emptyList()
    }

    override fun mapToLocalData(remoteData: List<CategoryDataItem>): List<Category> {
        return remoteData.toDomainList()
    }

}