package net.laggedhero.myreceipts.ui.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.laggedhero.myreceipts.core.data.model.User
import net.laggedhero.myreceipts.core.receipt.ReceiptDeleteModel
import net.laggedhero.myreceipts.core.receipt.ReceiptDeleteUseCase
import net.laggedhero.myreceipts.core.receipt.list.ReceiptListModel
import net.laggedhero.myreceipts.core.receipt.list.ReceiptListUseCase
import net.laggedhero.myreceipts.core.user.list.UserListModel
import net.laggedhero.myreceipts.core.user.list.UserListUseCase

class MainActivityViewModel(
    userListUseCase: UserListUseCase,
    private val receiptListUseCase: ReceiptListUseCase,
    private val receiptDeleteUseCase: ReceiptDeleteUseCase
) : ViewModel() {

    val userListModelObservable = ObservableField<UserListModel>()
    val receiptListModelObservable = ObservableField<ReceiptListModel>()
    val receiptDeleteModelObservable = ObservableField<ReceiptDeleteModel>()
    val isLoading =
        object : ObservableBoolean(
            userListModelObservable,
            receiptListModelObservable,
            receiptDeleteModelObservable
        ) {
            override fun get(): Boolean {
                return userListModelObservable.get()?.isLoading == true
                        || receiptListModelObservable.get()?.isLoading == true
                        || receiptDeleteModelObservable.get()?.isLoading == true
            }
        }
    val receipts =
        object : ObservableField<List<ReceiptListItemViewModel>>(receiptListModelObservable) {
            override fun get(): List<ReceiptListItemViewModel>? {
                return receiptListModelObservable.get()
                    ?.receipts?.map { ReceiptListItemViewModel(it) } ?: listOf()
            }
        }
    val errorMessage = ObservableField<String>()

    private var userDisposable: Disposable? = null
    private var receiptDisposable: Disposable? = null
    private var deleteReceiptDisposable: Disposable? = null
    private var user: User? = null

    init {
        userDisposable = userListUseCase.fetchUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userListModel ->
                userListModel.errorMessage?.let { errorMessage.set(it) }
                userListModelObservable.set(userListModel)
            }
    }

    override fun onCleared() {
        userDisposable?.dispose()
        receiptDisposable?.dispose()
        deleteReceiptDisposable?.dispose()
        super.onCleared()
    }

    fun deleteReceipt(position: Int) {
        deleteReceiptDisposable?.dispose()
        val user = user ?: return
        val receipt = receiptListModelObservable.get()?.receipts?.get(position) ?: return
        deleteReceiptDisposable = receiptDeleteUseCase.delete(user, receipt)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { receiptDeleteModel ->
                receiptDeleteModel.errorMessage?.let { errorMessage.set(it) }
                receiptDeleteModelObservable.set(receiptDeleteModel)
                if (!receiptDeleteModel.isLoading && receiptDeleteModel.errorMessage == null) {
                    receiptDisposable?.dispose()
                    loadUserReceipts()
                }
            }
    }

    fun onUserSelected(position: Int) {
        clearReceipts()
        user = userListModelObservable.get()?.users?.get(position) ?: return
        loadUserReceipts()
    }

    private fun clearReceipts() {
        receiptDisposable?.dispose()
        receiptListModelObservable.set(ReceiptListModel())
    }

    private fun loadUserReceipts() {
        val user = user ?: return
        receiptDisposable = receiptListUseCase.fetchFrom(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { receiptListModel ->
                receiptListModel.errorMessage?.let { errorMessage.set(it) }
                receiptListModelObservable.set(receiptListModel)
            }
    }
}