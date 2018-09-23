package net.laggedhero.myreceipts.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import net.laggedhero.myreceipts.persistence.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): Single<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(vararg userEntities: UserEntity)
}