package com.nisum.challenge.common.networking

import android.content.res.Resources
import com.nisum.challenge.R
import java.net.SocketException
import java.net.UnknownHostException

/**
 * Utilidad para envio de mensaje según las Exceptions.
 */

object NetworkUtils {

    const val ERR_DEFAULT_MSG = R.string.err_default_msg
    const val ERR_NO_INTERNET = R.string.err_no_internet
    const val ERR_TIMEOUT = R.string.err_timeout

    fun getErrorMessage(resources: Resources, t: Exception) = with(resources) {
        when (t) {
            is UnknownHostException ->
                getString(ERR_NO_INTERNET)
            is SocketException ->
                getString(ERR_TIMEOUT)
            else -> getString(ERR_DEFAULT_MSG)
        }
    }
}
