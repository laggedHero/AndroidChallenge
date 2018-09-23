package net.laggedhero.myreceipts.core.receipt.list

import androidx.annotation.VisibleForTesting
import io.reactivex.Completable
import io.reactivex.Flowable
import net.laggedhero.myreceipts.core.data.ReceiptRepository
import net.laggedhero.myreceipts.core.data.model.Receipt
import net.laggedhero.myreceipts.core.data.model.User

class ReceiptListUseCase(
    private val localReceiptRepository: ReceiptRepository,
    private val remoteReceiptRepository: ReceiptRepository
) {
    fun fetchFrom(user: User): Flowable<ReceiptListModel> {
        return fetchLocalFrom(user)
            .flatMap {
                if (it.isEmpty()) {
                    fetchRemoteFrom(user)
                        .andThen(fetchLocalFrom(user))
                } else {
                    Flowable.just(it)
                }
            }
            .map { ReceiptListModel(it) }
            .startWith(ReceiptListModel(isLoading = true))
            .onErrorResumeNext { _: Throwable ->
                fetchLocalFrom(user)
                    .map { ReceiptListModel(it, errorMessage = "Error loading from network") }
            }
    }

    @VisibleForTesting
    fun fetchRemoteFrom(user: User): Completable {
        return remoteReceiptRepository.getAllFrom(user)
            .map { receipts -> receipts.distinctBy { it.id } }
            .flatMapCompletable { receipts -> localReceiptRepository.saveAll(user, receipts) }
    }

    private fun fetchLocalFrom(user: User): Flowable<List<Receipt>> {
        return localReceiptRepository.getAllFrom(user).toFlowable()
    }
}
