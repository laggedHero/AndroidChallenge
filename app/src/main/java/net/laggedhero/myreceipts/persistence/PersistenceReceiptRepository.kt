package net.laggedhero.myreceipts.persistence

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.myreceipts.core.data.ReceiptRepository
import net.laggedhero.myreceipts.core.data.model.Receipt
import net.laggedhero.myreceipts.core.data.model.User
import net.laggedhero.myreceipts.persistence.model.toEntity

class PersistenceReceiptRepository(private val receiptDao: ReceiptDao) : ReceiptRepository {
    override fun getAllFrom(user: User): Single<List<Receipt>> {
        return receiptDao.getAllFromUser(user.id)
            .map { receiptEntities -> receiptEntities.map { receiptEntity -> receiptEntity.toReceipt() } }
    }

    override fun getById(user: User, id: String): Single<Receipt> {
        return receiptDao.getById(id, user.id)
            .map { receiptEntity -> receiptEntity.toReceipt() }
    }

    override fun saveAll(user: User, receipts: List<Receipt>): Completable {
        return Completable.fromAction {
            receiptDao.saveAll(*receipts.map { it.toEntity(user.id) }.toTypedArray())
        }
    }

    override fun delete(user: User, receipt: Receipt): Completable {
        return Completable.fromAction {
            receiptDao.delete(receipt.toEntity(user.id))
        }
    }
}
