package com.nisum.challenge.common

sealed class UIEvent

data class Message(val message: String?) : UIEvent()