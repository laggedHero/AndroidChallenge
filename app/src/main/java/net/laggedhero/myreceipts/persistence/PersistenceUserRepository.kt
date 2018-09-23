package net.laggedhero.myreceipts.persistence

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.myreceipts.core.data.UserRepository
import net.laggedhero.myreceipts.core.data.model.User
import net.laggedhero.myreceipts.persistence.model.toEntity

class PersistenceUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getAll(): Single<List<User>> {
        return userDao.getAll()
            .map { userEntities -> userEntities.map { userEntity -> userEntity.toUser() } }
    }

    override fun saveAll(users: List<User>): Completable {
        return Completable.fromAction {
            userDao.saveAll(*users.map { it.toEntity() }.toTypedArray())
        }
    }
}
