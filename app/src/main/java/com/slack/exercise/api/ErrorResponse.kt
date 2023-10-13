package com.slack.exercise.api

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String? = null,
    val ok: Boolean? = null,
    val users: List<String>? = null
)
