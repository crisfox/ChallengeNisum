package com.nisum.challenge.common.networking

import android.content.res.Resources
import com.google.gson.Gson
import com.nisum.challenge.common.networking.model.AppServerError
import retrofit2.Response

inline fun <reified T, reified E> execute(
    resources: Resources,
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
                        ?: resources.getString(NetworkUtils.ERR_DEFAULT_MSG)
                Unsuccessful(
                    error = errorString,
                    code = code,
                    message = errorString
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Unsuccessful(
                    error = NetworkUtils.getErrorMessage(resources, e),
                    code = code,
                    message = NetworkUtils.getErrorMessage(resources, e)
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        NetworkException(message = NetworkUtils.getErrorMessage(resources, e), exception = e)
    }
}

inline fun <reified T> execute(resources: Resources, serviceCall: () -> Response<T>): AppNetworkResult<T> {
    return execute(resources, serviceCall) { it }
}