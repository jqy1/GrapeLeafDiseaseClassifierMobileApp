package nz.ac.canterbury.cosc680.plantplus.app.utils

import android.util.Log
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*


class SSLAgent {

    fun trustAllHttpsCertificates() {
        try {
            val trustAllCerts = arrayOfNulls<TrustManager>(1)
            val tm: TrustManager = MyTrustManager()
            trustAllCerts[0] = tm
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, trustAllCerts, null)
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier(mHostnameVerifier)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val mHostnameVerifier =
        HostnameVerifier { hostname, session ->
            log("hostname:$hostname")
            true
        }

    private class MyTrustManager : TrustManager, X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate>? {
            return null
        }

        fun isServerTrusted(certs: Array<X509Certificate?>?): Boolean {
            return true
        }

        fun isClientTrusted(certs: Array<X509Certificate?>?): Boolean {
            return true
        }

        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
            return
        }

        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
            return
        }
    }

    private fun log(msg: String) {
        if (DEBUG) {
            Log.i(TAG, msg)
        }
    }

    companion object {
        private const val TAG = "SSLAgent"
        private const val DEBUG = true
        private var mSSLAgent: SSLAgent? = null
        val instance: SSLAgent?
            get() {
                if (mSSLAgent == null) {
                    mSSLAgent = SSLAgent()
                }
                return mSSLAgent
            }
    }
}

fun getUnsafeSSLSocketFactory(): SSLSocketFactory? {
    return try {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                    return arrayOf()
                }
            }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        sslContext.socketFactory
    } catch (e: java.lang.Exception) {
        throw RuntimeException(e)
    }
}

class TrustAllHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return true
    }
}