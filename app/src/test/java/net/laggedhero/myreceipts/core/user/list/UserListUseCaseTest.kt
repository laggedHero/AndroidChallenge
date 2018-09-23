package net.laggedhero.myreceipts.core.user.list

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.myreceipts.core.data.UserRepository
import net.laggedhero.myreceipts.core.data.model.Address
import net.laggedhero.myreceipts.core.data.model.Company
import net.laggedhero.myreceipts.core.data.model.Geo
import net.laggedhero.myreceipts.core.data.model.User
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

import org.mockito.MockitoAnnotations

class UserListUseCaseTest {

    @Mock
    lateinit var localUserRepository: UserRepository

    @Mock
    lateinit var remoteUserRepository: UserRepository

    private lateinit var userListUseCase: UserListUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userListUseCase = UserListUseCase(localUserRepository, remoteUserRepository)
    }

    // region fetchRemoteUsers
    @Test
    fun `fetchRemoteUsers should save the user list to local repository`() {
        `when`(remoteUserRepository.getAll())
            .thenReturn(Single.just(getUsers()))
        `when`(localUserRepository.saveAll(anyList()))
            .thenReturn(Completable.complete())


        userListUseCase.fetchRemoteUsers()
            .test()
            .assertComplete()

        verify(localUserRepository, times(1))
            .saveAll(getUsers())
    }
    // endregion

    // region fetchUsers
    @Test
    fun `fetchUsers should start with a loading state`() {
        `when`(remoteUserRepository.getAll())
            .thenReturn(Single.just(getUsers()))
        `when`(localUserRepository.saveAll(anyList()))
            .thenReturn(Completable.complete())
        `when`(localUserRepository.getAll())
            .thenReturn(Single.never())


        userListUseCase.fetchUsers()
            .test()
            .assertNoErrors()
            .assertNotComplete()
            .assertValue(UserListModel(listOf(), true))
    }

    @Test
    fun `fetchUsers should return user list and loading false when done`() {
        `when`(remoteUserRepository.getAll())
            .thenReturn(Single.just(getUsers()))
        `when`(localUserRepository.saveAll(anyList()))
            .thenReturn(Completable.complete())
        `when`(localUserRepository.getAll())
            .thenReturn(Single.just(getUsers()))


        userListUseCase.fetchUsers()
            .test()
            .assertComplete()
            .assertResult(
                UserListModel(listOf(), true),
                UserListModel(getUsers(), false)
            )
    }

    @Test
    fun `fetchUsers should return the local user list, loading done and error message when network fails`() {
        `when`(remoteUserRepository.getAll())
            .thenReturn(Single.error(Throwable()))
        `when`(localUserRepository.getAll())
            .thenReturn(Single.just(getUsers()))


        userListUseCase.fetchUsers()
            .test()
            .assertComplete()
            .assertResult(
                UserListModel(listOf(), true),
                UserListModel(getUsers(), false, "Error loading from network")
            )

        verify(localUserRepository, times(0))
            .saveAll(getUsers())
    }
    // endregion

    // region helper methods
    private fun getUsers(): List<User> {
        return listOf(
            User(
                1,
                "name-1",
                "username-1",
                "email-1",
                Address("street-1", "suite-1", "city-1", "zipcode-1", Geo("lat-1", "lng-1")),
                "phone-1",
                "website-1",
                Company("name-1", "catchPhrase-1", "bs")
            )
        )
    }
    // endregion
}