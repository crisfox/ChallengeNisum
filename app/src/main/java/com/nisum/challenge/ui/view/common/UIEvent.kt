package com.nisum.challenge.ui.view.common

/**
 * Para el manejo de eventos en caso de errores.
 */
sealed class UIEvent

data class Message(val message: String?) : UIEvent()
