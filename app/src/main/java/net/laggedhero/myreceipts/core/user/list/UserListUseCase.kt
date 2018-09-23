package net.laggedhero.myreceipts.core.user.list

import androidx.annotation.VisibleForTesting
import io.reactivex.Completable
import io.reactivex.Flowable
import net.laggedhero.myreceipts.core.data.UserRepository
import net.laggedhero.myreceipts.core.data.model.User

class UserListUseCase(
    private val localUserRepository: UserRepository,
    private val remoteUserRepository: UserRepository
) {
    fun fetchUsers(): Flowable<UserListModel> {
        return fetchRemoteUsers()
            .andThen(fetchLocalUsers())
            .map { UserListModel(it, false) }
            .startWith(UserListModel(listOf(), true))
            .onErrorResumeNext { _: Throwable ->
                fetchLocalUsers()
                    .map { UserListModel(it, false, "Error loading from network") }
            }
    }

    @VisibleForTesting
    fun fetchRemoteUsers(): Completable {
        return remoteUserRepository.getAll()
            .flatMapCompletable { users -> localUserRepository.saveAll(users) }
    }

    private fun fetchLocalUsers(): Flowable<List<User>> {
        return localUserRepository.getAll().toFlowable()
    }
}
