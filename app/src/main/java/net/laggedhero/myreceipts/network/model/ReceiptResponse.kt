package net.laggedhero.myreceipts.network.model

import net.laggedhero.myreceipts.core.data.model.Receipt

data class ReceiptResponse(val transactions: List<Receipt>)
