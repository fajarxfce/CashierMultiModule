package com.fajarxfce.feature.pos.data.model

import com.google.gson.annotations.SerializedName

internal data class GetCategoryByQueryResponse(

    @field:SerializedName("per_page")
	val perPage: Int? = null,

    @field:SerializedName("data")
	val data: List<CategoryDataItem?>? = null,

    @field:SerializedName("last_page")
	val lastPage: Int? = null,

    @field:SerializedName("next_page_url")
	val nextPageUrl: Any? = null,

    @field:SerializedName("prev_page_url")
	val prevPageUrl: Any? = null,

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
	val links: List<LinksCategoryItem?>? = null,

    @field:SerializedName("to")
	val to: Int? = null,

    @field:SerializedName("current_page")
	val currentPage: Int? = null
)

internal data class LinksCategoryItem(

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("url")
	val url: Any? = null
)

data class CategoryDataItem(

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
