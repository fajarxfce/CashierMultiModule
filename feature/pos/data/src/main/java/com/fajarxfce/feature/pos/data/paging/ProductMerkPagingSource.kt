package com.fajarxfce.feature.pos.data.paging

import com.fajarxfce.core.data.BasePagingSource
import com.fajarxfce.feature.pos.data.mapper.toDomainList
import com.fajarxfce.feature.pos.data.model.ProductMerkDataItem
import com.fajarxfce.feature.pos.data.source.PosApi
import com.fajarxfce.feature.pos.domain.model.Merk
import com.fajarxfce.feature.pos.domain.params.GetProductMerkByQueryParams
import com.fajarxfce.feature.pos.domain.params.toQueryMap

internal class ProductMerkPagingSource(
    private val posApi: PosApi,
    private val initialParams: GetProductMerkByQueryParams
) : BasePagingSource<Merk, ProductMerkDataItem>() {
    override suspend fun fetchData(
        page: Int,
        pageSize: Int,
    ): List<ProductMerkDataItem> {
        val paramsForApiCall = initialParams.copy(page = page)
        val response = posApi.getProductMerkByQuery(paramsForApiCall.toQueryMap())
        return response.data
            ?.data
            ?.filterNotNull()
            ?: emptyList()
    }

    override fun mapToLocalData(remoteData: List<ProductMerkDataItem>): List<Merk> {
        return remoteData.toDomainList()
    }

}