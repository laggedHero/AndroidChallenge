package net.laggedhero.myreceipts.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import net.laggedhero.myreceipts.persistence.model.ReceiptEntity
import net.laggedhero.myreceipts.persistence.model.UserEntity

@Database(entities = [UserEntity::class, ReceiptEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getReceiptDao(): ReceiptDao
}
