package com.slack.exercise.util.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.lang.Exception

@Parcelize
sealed class State<out R>: Parcelable {
    data object Loading: State<Nothing>()
    data class Success<T>( val value: @RawValue T): State<T>()
    data class Error(val exception: Exception): State<Nothing>()
}

fun <T> success(block: () -> T): State.Success<T> {
    return State.Success(block())
}

fun <T : Exception> error(block: () -> T): State.Error {
    return State.Error(block())
}

fun <T> State<T>.asSuccess(): T? =
    (this as? State.Success<T>)?.value