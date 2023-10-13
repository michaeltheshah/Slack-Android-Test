package com.slack.exercise.util.state

import com.slack.exercise.api.ErrorResponse
import okhttp3.Response

sealed interface NetworkResult<out R> {
    data class Ok<out T>(val value: T, val response: Response): NetworkResult<T>
    data class Error(val exception: Exception,
                     val response: Response? = null,
                     val jsonError: String? = null,
                     val errorResponse: ErrorResponse? = null): NetworkResult<Nothing>
}

fun <T, R> NetworkResult<T>.transform(block: (T) -> R): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Ok -> {
            NetworkResult.Ok(block(value), response)
        }
        is NetworkResult.Error -> {
            this
        }
    }
}

fun <T> NetworkResult<T>.toState(): State<T> {
    return when (this) {
        is NetworkResult.Ok -> {
            success { value }
        }
        is NetworkResult.Error -> {
            error { exception }
        }
    }
}