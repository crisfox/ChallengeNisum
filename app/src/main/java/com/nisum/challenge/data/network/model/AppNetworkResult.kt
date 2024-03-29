package com.nisum.challenge.data.network.model

/**
 * Estados para el manejo de respuesta de las llamadas http.
 *
 * @param T
 * @property data T?
 * @property code Int? codigo de error.
 * @property message String? mensaje adjunto.
 * @property error String? mensaje de error.
 * @property exception Exception? si hubo alguna exception.
 * @property state State Tipos de estado para identificarlo.
 */
sealed class AppNetworkResult<T>(
    override val data: T? = null,
    override val code: Int? = null,
    override val message: String? = null,
    override val error: String? = null,
    override val exception: Exception? = null,
    override val state: State
) : NetworkResult<T, String>(
    data, code, message, exception, error, state
)

data class Success<T>(
    override val data: T,
    override val code: Int,
    override val message: String? = null
) :
    AppNetworkResult<T>(data, code = code, message = message, state = State.SUCCESS)

data class Unsuccessful<T>(
    override val data: T? = null,
    override val code: Int,
    override val error: String,
    override val message: String?
) : AppNetworkResult<T>(
    data,
    code = code,
    error = error,
    message = message,
    state = State.UNSUCCESSFUL
)

data class NetworkException<T>(
    override val data: T? = null,
    override val exception: Exception,
    override val message: String?
) : AppNetworkResult<T>(data, exception = exception, message = message, state = State.ERROR)

data class Loading<T>(override val data: T? = null) :
    AppNetworkResult<T>(data, state = State.LOADING)
