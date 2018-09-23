package net.laggedhero.myreceipts.network

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.myreceipts.core.data.UserRepository
import net.laggedhero.myreceipts.core.data.model.User
import net.laggedhero.myreceipts.network.api.UserApi

class NetworkUserRepository(private val userApi: UserApi) : UserRepository {
    override fun getAll(): Single<List<User>> {
        return userApi.getUsers()
    }

    override fun saveAll(users: List<User>): Completable {
        return Completable.complete()
    }
}
