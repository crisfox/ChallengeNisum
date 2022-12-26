package com.nisum.challenge.common.networking

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission

/**
 * Monitorea la conexion a internet y lo notifica.
 *
 * @property application Application contexto para obtener información del dispositivo.
 * @property networkCallback Callback para evaluar si existe una conexión a internet o no.
 * @constructor
 */
class NetworkMonitor
@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
constructor(private val application: Application) {

    fun startNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        /**Check if version code is greater than API 24*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            cm.registerNetworkCallback(
                builder.build(), networkCallback
            )
        }
    }

    fun stopNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            Variables.isNetworkConnected = true
        }

        override fun onLost(network: Network) {
            Variables.isNetworkConnected = false
        }
    }
}
