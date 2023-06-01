package com.nisum.challenge.data.network.model

open class NetworkResult<T, E>(
    open val data: T? = null,
    open val code: Int? = null,
    open val message: String? = null,
    open val exception: Exception? = null,
    open val error: E? = null,
    open val state: State
)

enum class State { SUCCESS, UNSUCCESSFUL, ERROR, LOADING }
