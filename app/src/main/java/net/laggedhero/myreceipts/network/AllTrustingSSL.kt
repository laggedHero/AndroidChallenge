package net.laggedhero.myreceipts.network

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object AllTrustingSSL {
    fun createOkHttpClient(): OkHttpClient {
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            val sslSocketFactory: SSLSocketFactory = SSLContext.getInstance("SSL")
                .apply { init(null, trustAllCerts, java.security.SecureRandom()) }
                .socketFactory

            return with(OkHttpClient.Builder()) {
                sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                hostnameVerifier { _, _ -> true }
                build()
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
