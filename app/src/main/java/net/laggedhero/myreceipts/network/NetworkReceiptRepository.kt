package net.laggedhero.myreceipts.network

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.myreceipts.core.data.ReceiptRepository
import net.laggedhero.myreceipts.core.data.model.Receipt
import net.laggedhero.myreceipts.core.data.model.User
import net.laggedhero.myreceipts.network.api.ReceiptApi

class NetworkReceiptRepository(private val receiptApi: ReceiptApi) : ReceiptRepository {
    override fun getAllFrom(user: User): Single<List<Receipt>> {
        return receiptApi.getFromUser(user.id).map { it.transactions }
    }

    override fun getById(user: User, id: String): Single<Receipt> {
        return Single.error(NotImplementedError())
    }

    override fun saveAll(user: User, receipts: List<Receipt>): Completable {
        return Completable.error(NotImplementedError())
    }

    override fun delete(user: User, receipt: Receipt): Completable {
        return Completable.error(NotImplementedError())
    }
}
