package com.fajarxfce.feature.cart.data.model

import com.google.gson.annotations.SerializedName

data class CreateTransactionResponse(

	@field:SerializedName("reference")
	val reference: String? = null,

	@field:SerializedName("ref_number")
	val refNumber: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("discount_total")
	val discountTotal: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("discount_flat")
	val discountFlat: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("grand_total")
	val grandTotal: Int? = null,

	@field:SerializedName("discount_percent")
	val discountPercent: Any? = null
)
