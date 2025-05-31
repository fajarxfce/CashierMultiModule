package com.fajarxfce.feature.transactionhistory.domain.model

data class TransactionHistory(
    val id: Int?,
    val userId: Int?,
    val reference: String?,
    val refNumber: Int?,
    val total: Int?,
    val discountFlat: Int? = null,
    val discountPercent: Int? = null,
    val discountTotal: Int? = null,
    val grandTotal: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val user: User?
)

data class User(
    val id: Int?,
    val name: String?,
    val email: String?
)