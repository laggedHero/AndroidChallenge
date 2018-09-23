package net.laggedhero.myreceipts.core.receipt

import io.reactivex.Flowable
import net.laggedhero.myreceipts.core.data.ReceiptRepository
import net.laggedhero.myreceipts.core.data.model.Receipt
import net.laggedhero.myreceipts.core.data.model.User

class ReceiptDeleteUseCase(private val localReceiptRepository: ReceiptRepository) {
    fun delete(user: User, receipt: Receipt): Flowable<ReceiptDeleteModel> {
        return localReceiptRepository.delete(user, receipt)
            .andThen(Flowable.just(ReceiptDeleteModel()))
            .startWith(ReceiptDeleteModel(true))
            .onErrorReturnItem(ReceiptDeleteModel(errorMessage = "Error deleting"))
    }
}
