package net.laggedhero.myreceipts.core.data

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.myreceipts.core.data.model.User

interface UserRepository {
    fun getAll(): Single<List<User>>
    fun saveAll(users: List<User>): Completable
}
