package net.laggedhero.myreceipts.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import net.laggedhero.myreceipts.persistence.model.ReceiptEntity

@Dao
interface ReceiptDao {
    @Query("SELECT * FROM ReceiptEntity WHERE user_id = :userId")
    fun getAllFromUser(userId: Int): Single<List<ReceiptEntity>>

    @Query("SELECT * FROM ReceiptEntity WHERE id = :id AND user_id = :userId")
    fun getById(id: String, userId: Int): Single<ReceiptEntity>

    @Insert
    fun saveAll(vararg receiptEntities: ReceiptEntity)

    @Delete
    fun delete(receiptEntity: ReceiptEntity)
}
