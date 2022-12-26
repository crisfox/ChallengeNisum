package com.nisum.challenge.common.networking

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.mockito.Mockito
import java.io.IOException
import java.nio.charset.Charset

inline fun <reified T> Gson.fromJson(json: String): T =
    this.fromJson(json, object : TypeToken<T>() {}.type)
