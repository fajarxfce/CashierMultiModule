package com.fajarxfce.feature.pos.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState      // Menggunakan konstanta dari core
import com.fajarxfce.core.data.BasePagingSource
import com.fajarxfce.core.data.DEFAULT_INITIAL_PAGE_NUMBER
import com.fajarxfce.core.data.DEFAULT_PAGE_SIZE
import com.fajarxfce.feature.pos.data.mapper.toDomainList
import com.fajarxfce.feature.pos.data.model.ProductDataItem
import com.fajarxfce.feature.pos.data.source.PosApi
import com.fajarxfce.feature.pos.domain.model.Product
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class ProductPagingSource @Inject constructor(
    private val posApi: PosApi,
    private val query: String?,
    private val categoryId: String?
) : BasePagingSource<Product, ProductDataItem>() {
    override suspend fun fetchData(
        page: Int,
        pageSize: Int,
    ): List<ProductDataItem> {
        val response = posApi.getProducts(
            pageNumber = page,
            pageSize = pageSize,
            query = query,
            categoryId = categoryId,
            token = "Bearer 115|peniuAsULVfkHPKjCCfWyvjRI3ImtWLW6WYckgXxcd93bf32"
        )
        return response.data
            ?.data
            ?.filterNotNull()
            ?: emptyList()
    }

    override fun mapToLocalData(remoteData: List<ProductDataItem>): List<Product> {
        return remoteData.toDomainList()
    }

}