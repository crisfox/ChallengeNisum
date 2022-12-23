package com.nisum.challenge.common.networking

import java.net.SocketException
import java.net.UnknownHostException

object NetworkUtils {

    const val ERR_DEFAULT_MSG = "Something went wrong! Please try again later."
    private const val ERR_NO_INTERNET = "No Internet Connection!"
    private const val ERR_TIMEOUT = "Connection Timeout"

    @JvmStatic
    fun getErrorMessage(t: Exception) = when (t) {
        is UnknownHostException ->
            ERR_NO_INTERNET
        is SocketException ->
            ERR_TIMEOUT
        else -> ERR_DEFAULT_MSG
    }
}