package com.fajarxfce.feature.pos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fajarxfce.feature.pos.data.paging.CategoryPagingSource
import com.fajarxfce.feature.pos.data.paging.ProductMerkPagingSource
import com.fajarxfce.feature.pos.domain.params.GetAllProductParams
import com.fajarxfce.feature.pos.data.paging.ProductPagingSource
import com.fajarxfce.feature.pos.data.source.PosApi
import com.fajarxfce.feature.pos.domain.model.Category
import com.fajarxfce.feature.pos.domain.model.Merk
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.params.GetCategoryByQueryParams
import com.fajarxfce.feature.pos.domain.params.GetProductMerkByQueryParams
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PosRepositoryImpl @Inject constructor(
    private val posApi: PosApi,
) : PosRepository {

    override fun getProducts(
        params: GetAllProductParams,
    ): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = params.paginate,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {

                ProductPagingSource(posApi = posApi, initialParams = params)
            },
        ).flow
    }

    override fun getCategoryByQuery(params: GetCategoryByQueryParams): Flow<PagingData<Category>> {
        return Pager(
            config = PagingConfig(
                pageSize = params.paginate,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                CategoryPagingSource(posApi = posApi, initialParams = params)
            }
        ).flow
    }

    override fun getProductMerkByQuery(params: GetProductMerkByQueryParams): Flow<PagingData<Merk>> {
        return Pager(
            config = PagingConfig(
                pageSize = params.paginate,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductMerkPagingSource(posApi = posApi, initialParams = params) }
        ).flow
    }
}