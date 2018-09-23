package net.laggedhero.myreceipts.network.api

import io.reactivex.Single
import net.laggedhero.myreceipts.network.model.ReceiptResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReceiptApi {
    @GET("5b648e472e000067004140d9")
    fun getFromUser(@Query("userid") id: Int): Single<ReceiptResponse>
}
