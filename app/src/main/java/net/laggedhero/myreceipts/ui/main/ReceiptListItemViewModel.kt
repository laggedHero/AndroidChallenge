package net.laggedhero.myreceipts.ui.main

import net.laggedhero.myreceipts.core.data.model.Receipt

data class ReceiptListItemViewModel(private val receipt: Receipt) {
    val description get() = receipt.transactionDescription
    val value get() = "${receipt.amount} ${receipt.currencyCode}"
    val date: String
        get() {
            val dateParts = receipt.date.split('T')[0].split('-')
            return "${dateParts[1]}/${dateParts[2]}/${dateParts[0]}"
        }
}
