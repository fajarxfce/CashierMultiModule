package com.fajarxfce.feature.transactionhistory.data.mapper

import com.fajarxfce.feature.transactionhistory.data.model.TransactionDataItem
import com.fajarxfce.feature.transactionhistory.domain.model.TransactionHistory
import com.fajarxfce.feature.transactionhistory.domain.model.User

fun TransactionDataItem.toDomain() : TransactionHistory =
    TransactionHistory(
        id = this.id,
        userId = this.userId,
        reference = this.reference,
        refNumber = this.refNumber,
        total = this.total,
        discountFlat = this.discountFlat,
        discountPercent = this.discountPercent,
        discountTotal = this.discountTotal,
        grandTotal = this.grandTotal,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        user = User(
            id = this.user?.id,
            name = this.user?.name,
            email = this.user?.email
        ),
    )

fun List<TransactionDataItem>.toDomainList(): List<TransactionHistory>  {
    return this.mapNotNull { it?.toDomain() }
}
