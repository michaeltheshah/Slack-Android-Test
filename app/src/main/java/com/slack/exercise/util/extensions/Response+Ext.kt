package com.slack.exercise.util.extensions

import com.slack.exercise.api.ErrorResponse
import com.slack.exercise.util.state.NetworkResult
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.toNetworkResult(): NetworkResult<T> {
    return try {
        if (isSuccessful) {
            val body = body()
            if (body != null) {
                NetworkResult.Ok(body, raw())
            } else {
                NetworkResult.Error(NullPointerException("Response body is null"), raw(), errorBody()?.string())
            }
        } else {
            NetworkResult.Error(HttpException(this), raw(), errorBody()?.string())
        }
    } catch (e: Exception) {
        val json = errorBody()?.string()
        val errorResponse = tryOrNull { Json.decodeFromString<ErrorResponse>(json!!) }
        NetworkResult.Error(e, raw(), errorBody()?.string(), errorResponse)
    }
}

val <T> Response<T>.value: T?
    get() = body()