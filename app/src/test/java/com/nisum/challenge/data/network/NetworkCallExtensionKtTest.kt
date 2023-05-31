package com.nisum.challenge.data.network

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nisum.challenge.common.fromJson
import com.nisum.challenge.data.network.model.AppServerError
import com.nisum.challenge.core.NetworkUtils
import com.nisum.challenge.core.execute
import com.nisum.challenge.data.network.model.NetworkException
import com.nisum.challenge.data.network.model.Unsuccessful
import okhttp3.ResponseBody
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Response
import java.net.SocketException
import kotlin.test.Test
import kotlin.test.assertEquals

class NetworkCallExtensionKtTest {

    private var mockResourcesMock: Resources = mock {
        on { getString(NetworkUtils.ERR_TIMEOUT) } doReturn "Connection Timeout"
        on { getString(NetworkUtils.ERR_DEFAULT_MSG) } doReturn "Something went wrong! Please try again later."
        on { getString(NetworkUtils.ERR_NO_INTERNET) } doReturn "No Internet Connection!"
    }

    @Test
    fun `success case`() {
        val body = "Abc"
        val code = 200
        val result = execute(mockResourcesMock) { Response.success(code, body) }
        assertEquals(200, result.code)
        assertEquals("Abc", result.data)
    }

    @Test
    fun `unsuccessful case`() {
        val errorJson = JsonObject()
        errorJson.addProperty("error", "Bad Request")
        val errorModel = Gson().fromJson<AppServerError>(errorJson.toString())
        val error = Response.error<String>(404, ResponseBody.create(null, errorJson.toString()))
        val expectedError = Unsuccessful<String>(
            error = errorModel.error,
            code = 404,
            message = errorModel.error
        )
        val result = execute(mockResourcesMock) { error }
        assertEquals(expectedError, result)
    }

    @Test
    fun `error case`() {
        val exception = SocketException()
        val expectedError = NetworkException<String>(
            message = mockResourcesMock.getString(NetworkUtils.ERR_TIMEOUT),
            exception = exception
        )
        val result = execute<String, Map<String, String>>(
            mockResourcesMock,
            { throw exception },
            { result -> mapOf("data" to result) })

        assertEquals(expectedError.message, result.message)
    }
}
