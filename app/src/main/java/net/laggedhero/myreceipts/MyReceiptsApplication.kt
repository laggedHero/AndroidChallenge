package net.laggedhero.myreceipts

import android.app.Application
import net.laggedhero.myreceipts.injection.insertKoin

class MyReceiptsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        insertKoin()
    }
}
