package net.laggedhero.myreceipts.injection

import android.app.Application
import androidx.room.Room
import net.laggedhero.myreceipts.core.data.ReceiptRepository
import net.laggedhero.myreceipts.core.data.UserRepository
import net.laggedhero.myreceipts.core.receipt.ReceiptDeleteUseCase
import net.laggedhero.myreceipts.core.receipt.list.ReceiptListUseCase
import net.laggedhero.myreceipts.core.user.list.UserListUseCase
import net.laggedhero.myreceipts.network.AllTrustingSSL
import net.laggedhero.myreceipts.network.NetworkReceiptRepository
import net.laggedhero.myreceipts.network.NetworkUserRepository
import net.laggedhero.myreceipts.network.api.ReceiptApi
import net.laggedhero.myreceipts.network.api.UserApi
import net.laggedhero.myreceipts.persistence.AppDatabase
import net.laggedhero.myreceipts.persistence.PersistenceReceiptRepository
import net.laggedhero.myreceipts.persistence.PersistenceUserRepository
import net.laggedhero.myreceipts.ui.main.MainActivityViewModel
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://www.mocky.io/v2/")
            .client(AllTrustingSSL.createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ReceiptApi::class.java)
    }
    single<UserRepository>(name = "RemoteUserRepository") { NetworkUserRepository(get()) }
    single<ReceiptRepository>(name = "RemoteReceiptRepository") { NetworkReceiptRepository(get()) }
}

val persistenceModule = module {
    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "appDb").build() }
    single { get<AppDatabase>().getUserDao() }
    single { get<AppDatabase>().getReceiptDao() }
    single<UserRepository>(name = "LocalUserRepository") { PersistenceUserRepository(get()) }
    single<ReceiptRepository>(name = "LocalReceiptRepository") { PersistenceReceiptRepository(get()) }
}

val appModule = module {
    single {
        UserListUseCase(
            get(name = "LocalUserRepository"),
            get(name = "RemoteUserRepository")
        )
    }
    single {
        ReceiptListUseCase(
            get(name = "LocalReceiptRepository"),
            get(name = "RemoteReceiptRepository")
        )
    }
    single { ReceiptDeleteUseCase(get(name = "LocalReceiptRepository")) }
    viewModel { MainActivityViewModel(get(), get(), get()) }
}

fun Application.insertKoin() {
    startKoin(this, listOf(networkModule, persistenceModule, appModule))
}