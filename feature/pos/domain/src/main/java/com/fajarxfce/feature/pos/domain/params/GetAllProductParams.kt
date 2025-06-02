package com.fajarxfce.feature.pos.domain.params

import com.google.gson.annotations.SerializedName

data class GetAllProductParams (
    val orderBy: String = "products.id",
    val order: String = "asc",
    val paginate: Int = 10,
    val searchBySpecific: String? = null,
    val search: String? = null,
    val page: Int,
    @SerializedName("product_category_id")
    val productCategoryId: List<Int>? = null,
    @SerializedName("product_sub_category_id")
    val productSubCategoryId: List<Int>? = null,
    @SerializedName("product_merk_id")
    val productMerkId: List<Int>? = null,
)

fun GetAllProductParams.toQueryMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()

    map["orderBy"] = this.orderBy
    map["order"] = this.order
    map["paginate"] = this.paginate.toString()
    map["page"] = this.page.toString()

    this.searchBySpecific?.let { map["searchBySpecific"] = it }
    this.search?.let { map["search"] = it }

    this.productCategoryId?.forEachIndexed { index, categoryId ->
        map["product_category_id[$index]"] = categoryId.toString()
    }
    this.productMerkId?.forEachIndexed { index, merkId ->
        map["product_merk_id[$index]"] = merkId.toString()
    }
    this.productSubCategoryId?.forEachIndexed { index, subCategoryId ->
        map["product_sub_category_id[$index]"] = subCategoryId.toString()
    }
    return map
}