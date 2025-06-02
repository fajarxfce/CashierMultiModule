package com.fajarxfce.feature.pos.domain.params

import com.google.gson.annotations.SerializedName

data class GetCategoryByQueryParams (
    val orderBy: String = "products.id",
    val order: String = "asc",
    val paginate: Int = 10,
    val searchBySpecific: String? = null,
    val search: String? = null,
    val page: Int,
)

fun GetCategoryByQueryParams.toQueryMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()

    map["orderBy"] = this.orderBy
    map["order"] = this.order
    map["paginate"] = this.paginate.toString()
    map["page"] = this.page.toString()

    this.searchBySpecific?.let { map["searchBySpecific"] = it }
    this.search?.let { map["search"] = it }
    
    return map
}