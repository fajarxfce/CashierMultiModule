package com.fajarxfce.feature.cart.data.model

import com.google.gson.annotations.SerializedName

data class CreateTransactionRequest(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null
)
