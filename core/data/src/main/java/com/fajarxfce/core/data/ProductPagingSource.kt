package com.fajarxfce.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fajarxfce.core.data.remote.ProductRemoteDatasource
import com.fajarxfce.core.domain.usecase.product.GetAllProductParams
import com.fajarxfce.core.domain.usecase.product.OrderOption
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.network.response.toProduct
import okio.IOException

class ProductPagingSource(
    private val remoteDatasource: ProductRemoteDatasource,
    private val orderBy: String,
    private val ascending: Boolean,
    private val itemPerPage: Int = 10,
) : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 1
        return try {
            val response = remoteDatasource.getAllProduct(
                GetAllProductParams(
                    page = page,
                    paginate = itemPerPage,
                    orderBy = orderBy,
                    order = OrderOption(
                        ascending = ascending,
                    ),
                ),
            )

            val products = response.toProduct()

            LoadResult.Page(
                data = products,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (products.isEmpty()) null else page + 1,
            )

        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}