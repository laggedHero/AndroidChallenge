package net.laggedhero.myreceipts.core.receipt.list

import net.laggedhero.myreceipts.core.data.model.Receipt

data class ReceiptListModel(
    val receipts: List<Receipt> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
