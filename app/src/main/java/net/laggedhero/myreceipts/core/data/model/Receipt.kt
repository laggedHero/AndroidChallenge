package net.laggedhero.myreceipts.core.data.model

data class Receipt(
    val id: String,
    val postedDate: String,
    val accountId: String,
    val amount: String,
    val currencyCode: String,
    val showRunningBalance: Boolean,
    val date: String,
    val transactionDescription: String
)