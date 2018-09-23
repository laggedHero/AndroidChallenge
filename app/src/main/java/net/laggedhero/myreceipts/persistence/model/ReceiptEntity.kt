package net.laggedhero.myreceipts.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import net.laggedhero.myreceipts.core.data.model.Receipt

@Entity(
    primaryKeys = ["id", "user_id"],
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["user_id"])
    ]
)
data class ReceiptEntity(
    val id: String,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "posted_date") val postedDate: String,
    @ColumnInfo(name = "account_id") val accountId: String,
    val amount: String,
    @ColumnInfo(name = "currency_code") val currencyCode: String,
    @ColumnInfo(name = "show_running_balance") val showRunningBalance: Boolean,
    val date: String,
    @ColumnInfo(name = "transaction_description") val transactionDescription: String
) {
    fun toReceipt() = Receipt(
        id,
        postedDate,
        accountId,
        amount,
        currencyCode,
        showRunningBalance,
        date,
        transactionDescription
    )
}

fun Receipt.toEntity(userId: Int) = ReceiptEntity(
    id,
    userId,
    postedDate,
    accountId,
    amount,
    currencyCode,
    showRunningBalance,
    date,
    transactionDescription
)
