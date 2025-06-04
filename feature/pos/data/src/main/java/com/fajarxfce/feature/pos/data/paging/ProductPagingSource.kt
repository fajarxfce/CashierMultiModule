package com.fajarxfce.feature.pos.data.paging

import com.fajarxfce.core.base.BasePagingSource
import com.fajarxfce.feature.pos.data.mapper.toDomainList
import com.fajarxfce.feature.pos.domain.params.GetAllProductParams
import com.fajarxfce.feature.pos.data.model.ProductDataItem
import com.fajarxfce.feature.pos.domain.params.toQueryMap
import com.fajarxfce.feature.pos.data.source.PosApi
import com.fajarxfce.feature.pos.domain.model.Product

internal class ProductPagingSource(
    private val posApi: PosApi,
    private val initialParams: GetAllProductParams
) : BasePagingSource<Product, ProductDataItem>() {
    override suspend fun fetchData(
        page: Int,
        pageSize: Int,
    ): List<ProductDataItem> {
        val paramsForApiCall = initialParams.copy(page = page)
        val response = posApi.getProducts(paramsForApiCall.toQueryMap())
        return response.data
            ?.data
            ?.filterNotNull()
            ?: emptyList()
    }

    override fun mapToLocalData(remoteData: List<ProductDataItem>): List<Product> {
        return remoteData.toDomainList()
    }

}