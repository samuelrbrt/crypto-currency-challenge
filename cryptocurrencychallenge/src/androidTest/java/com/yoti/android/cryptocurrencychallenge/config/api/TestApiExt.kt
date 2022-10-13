package com.yoti.android.cryptocurrencychallenge.config.api

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

inline fun <reified T> getResponseData(context: Context, serializer: Gson, responseFile: String? = null): T {
    val caller = Throwable().stackTrace[0].methodName
    val filename = responseFile ?: caller.replace("([a-z])([A-Z]+)".toRegex(), "\$1_\$2").lowercase()

    val stream = context.resources.assets.open("api-responses/$filename.json")
    val reader = BufferedReader(InputStreamReader(stream))
    val lines = reader.readLines().joinToString(separator = "\n")
    reader.close()
    stream.close()
    return serializer.fromJson(lines, object : TypeToken<T>() {}.type)
}