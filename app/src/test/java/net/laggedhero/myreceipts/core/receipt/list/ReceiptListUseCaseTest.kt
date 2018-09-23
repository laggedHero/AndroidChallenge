package net.laggedhero.myreceipts.core.receipt.list

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.myreceipts.any
import net.laggedhero.myreceipts.core.data.ReceiptRepository
import net.laggedhero.myreceipts.core.data.model.Address
import net.laggedhero.myreceipts.core.data.model.Company
import net.laggedhero.myreceipts.core.data.model.Geo
import net.laggedhero.myreceipts.core.data.model.Receipt
import net.laggedhero.myreceipts.core.data.model.User
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ReceiptListUseCaseTest {

    @Mock
    lateinit var localReceiptRepository: ReceiptRepository

    @Mock
    lateinit var remoteReceiptRepository: ReceiptRepository

    private lateinit var receiptListUseCase: ReceiptListUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        receiptListUseCase = ReceiptListUseCase(localReceiptRepository, remoteReceiptRepository)
    }

    // region fetchRemoteUsers
    @Test
    fun `fetchRemoteUsers should save the receipt list to local repository`() {
        Mockito.`when`(remoteReceiptRepository.getAllFrom(any()))
            .thenReturn(Single.just(getReceipts()))
        Mockito.`when`(localReceiptRepository.saveAll(any(), anyList()))
            .thenReturn(Completable.complete())


        receiptListUseCase.fetchRemoteFrom(getUser())
            .test()
            .assertComplete()

        Mockito.verify(localReceiptRepository, Mockito.times(1))
            .saveAll(getUser(), getReceipts())
    }

    @Test
    fun `fetchRemoteUsers should remove entries with duplicated id`() {
        val receipts = getReceipts().apply {
            add(getReceipt(1, 2))
        }
        Mockito.`when`(remoteReceiptRepository.getAllFrom(any()))
            .thenReturn(Single.just(receipts))
        Mockito.`when`(localReceiptRepository.saveAll(any(), anyList()))
            .thenReturn(Completable.complete())


        receiptListUseCase.fetchRemoteFrom(getUser())
            .test()
            .assertComplete()

        Mockito.verify(localReceiptRepository, Mockito.times(1))
            .saveAll(getUser(), getReceipts())
    }
    // endregion

    // region fetchFrom
    @Test
    fun `fetchFrom does not hit the network when the cache is available`() {
        Mockito.`when`(localReceiptRepository.getAllFrom(any()))
            .thenReturn(Single.just(getReceipts()))


        receiptListUseCase.fetchFrom(getUser())
            .test()
            .assertComplete()

        Mockito.verify(remoteReceiptRepository, Mockito.times(0))
            .getAllFrom(any())
    }
    // endregion

    private fun getUser(): User {
        return User(
            1,
            "name-1",
            "username-1",
            "email-1",
            Address("street-1", "suite-1", "city-1", "zipcode-1", Geo("lat-1", "lng-1")),
            "phone-1",
            "website-1",
            Company("name-1", "catchPhrase-1", "bs")
        )
    }

    private fun getReceipts(): MutableList<Receipt> {
        return mutableListOf(
            getReceipt(1)
        )
    }

    private fun getReceipt(idValue: Int, otherValues: Int = idValue): Receipt {
        return Receipt(
            "id-$idValue",
            "postedDate-$otherValues",
            "accountId-$otherValues",
            "amount-$otherValues",
            "currencyCode-$otherValues",
            false,
            "date-$otherValues",
            "transactionDescription-$otherValues"
        )
    }
}