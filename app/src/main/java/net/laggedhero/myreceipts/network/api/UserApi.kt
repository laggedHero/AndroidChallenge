package net.laggedhero.myreceipts.network.api

import io.reactivex.Single
import net.laggedhero.myreceipts.core.data.model.User
import retrofit2.http.GET

interface UserApi {
    @GET("users")
    fun getUsers(): Single<List<User>>
}
