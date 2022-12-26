package com.nisum.challenge.common.networking

import kotlin.properties.Delegates

/**
 * Observa si ocurrio un cambio en la conexión a internet.
 */
object Variables {
    var isNetworkConnected: Boolean by Delegates.observable(false) { _, _, _ -> }
}
