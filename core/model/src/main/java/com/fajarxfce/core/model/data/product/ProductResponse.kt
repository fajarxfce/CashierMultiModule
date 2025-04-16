package com.fajarxfce.core.model.data.product

import com.google.gson.annotations.SerializedName


data class Product(

	@field:SerializedName("merk")
	val merk: String? = null,

	@field:SerializedName("sub_category")
	val subCategory: SubCategory? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("media")
	val media: List<MediaItem?>? = null,

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

data class SubCategory(

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

data class MediaItem(

	@field:SerializedName("manipulations")
	val manipulations: List<Any?>? = null,

	@field:SerializedName("order_column")
	val orderColumn: Int? = null,

	@field:SerializedName("file_name")
	val fileName: String? = null,

	@field:SerializedName("model_type")
	val modelType: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("model_id")
	val modelId: Int? = null,

	@field:SerializedName("custom_properties")
	val customProperties: List<Any?>? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("conversions_disk")
	val conversionsDisk: String? = null,

	@field:SerializedName("disk")
	val disk: String? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("generated_conversions")
	val generatedConversions: List<Any?>? = null,

	@field:SerializedName("responsive_images")
	val responsiveImages: List<Any?>? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("mime_type")
	val mimeType: String? = null,

	@field:SerializedName("original_url")
	val originalUrl: String? = null,

	@field:SerializedName("preview_url")
	val previewUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("collection_name")
	val collectionName: String? = null
)

data class Category(

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
