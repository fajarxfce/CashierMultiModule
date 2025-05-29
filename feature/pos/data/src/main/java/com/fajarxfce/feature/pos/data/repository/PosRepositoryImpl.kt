package com.fajarxfce.feature.pos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fajarxfce.core.data.DEFAULT_PAGE_SIZE
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.feature.pos.data.paging.ProductPagingSource
import com.fajarxfce.feature.pos.data.source.PosApi
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface AuthTokenProvider {
    suspend fun getAuthToken(): String?
}


@Singleton
class DemoAuthTokenProvider @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource
) : AuthTokenProvider {
    override suspend fun getAuthToken(): String = niaPreferencesDataSource.getAuthToken().orEmpty()
}


@Singleton
internal class PosRepositoryImpl @Inject constructor(
    private val posApi: PosApi,
    private val authTokenProvider: AuthTokenProvider
) : PosRepository {

    override fun getProducts(
        query: String?,
        categoryId: String?
    ): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = DEFAULT_PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {

                val DUMMY_TOKEN_UNTUK_FACTORY = "Bearer 109|LxwBhtpDa8OMzQJZtaywGVsrsuk2pIUf9Fkiwcs92e84c2e4"

                ProductPagingSource(
                    posApi = posApi,
                    authToken = DUMMY_TOKEN_UNTUK_FACTORY,
                    query = query,
                    categoryId = categoryId
                )
            }
        ).flow
    }
}