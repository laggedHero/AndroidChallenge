package net.laggedhero.myreceipts.core.data

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.myreceipts.core.data.model.Receipt
import net.laggedhero.myreceipts.core.data.model.User

interface ReceiptRepository {
    fun getAllFrom(user: User): Single<List<Receipt>>
    fun getById(user: User, id: String): Single<Receipt>
    fun saveAll(user: User, receipts: List<Receipt>): Completable
    fun delete(user: User, receipt: Receipt): Completable
}
