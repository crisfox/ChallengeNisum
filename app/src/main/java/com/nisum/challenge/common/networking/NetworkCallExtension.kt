package com.nisum.challenge.common.networking

import com.google.gson.Gson
import com.nisum.challenge.common.networking.model.AppServerError
import retrofit2.Response

inline fun <reified T, reified E> execute(
    serviceCall: () -> Response<T>,
    transform: (T) -> E
): AppNetworkResult<E> {
    return try {
        val data = serviceCall()
        if (data.isSuccessful) {
            Success(data = transform(data.body()!!), code = data.code())
        } else {
            val code = data.code()
            return try {
                val errorString =
                    data
                        .errorBody()
                        ?.string()
                        ?.let { Gson().fromJson(it, AppServerError::class.java) }?.error
                        ?: NetworkUtils.ERR_DEFAULT_MSG
                Unsuccessful(
                    error = errorString,
                    code = code,
                    message = errorString
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Unsuccessful(
                    error = NetworkUtils.getErrorMessage(e),
                    code = code,
                    message = NetworkUtils.getErrorMessage(e)
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        NetworkException(message = NetworkUtils.getErrorMessage(e), exception = e)
    }
}

inline fun <reified T> execute(serviceCall: () -> Response<T>): AppNetworkResult<T> {
    return execute(serviceCall) { it }
}