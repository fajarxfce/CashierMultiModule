package com.fajarxfce.feature.pos.data.model

import com.google.gson.annotations.SerializedName

internal data class GetAllProductResponse(

    @field:SerializedName("per_page")
	val perPage: Int? = null,

    @field:SerializedName("data")
	val data: List<ProductDataItem?>? = null,

    @field:SerializedName("last_page")
	val lastPage: Int? = null,

    @field:SerializedName("next_page_url")
	val nextPageUrl: String? = null,

    @field:SerializedName("prev_page_url")
	val prevPageUrl: String? = null,

    @field:SerializedName("first_page_url")
	val firstPageUrl: String? = null,

    @field:SerializedName("path")
	val path: String? = null,

    @field:SerializedName("total")
	val total: Int? = null,

    @field:SerializedName("last_page_url")
	val lastPageUrl: String? = null,

    @field:SerializedName("from")
	val from: Int? = null,

    @field:SerializedName("links")
	val links: List<LinksItem?>? = null,

    @field:SerializedName("to")
	val to: Int? = null,

    @field:SerializedName("current_page")
	val currentPage: Int? = null
)

internal data class Merk(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

internal data class ProductDataItem(

	@field:SerializedName("merk")
	val merk: Merk? = null,

	@field:SerializedName("sub_category")
	val subCategory: SubCategory? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("media")
	val media: List<Any?>? = null,

	@field:SerializedName("product_category_id")
	val productCategoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("product_sub_category_id")
	val productSubCategoryId: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("product_merk_id")
	val productMerkId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("sku")
	val sku: String? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

internal data class SubCategory(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("product_category_id")
	val productCategoryId: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

internal data class Category(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

internal data class LinksItem(

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
