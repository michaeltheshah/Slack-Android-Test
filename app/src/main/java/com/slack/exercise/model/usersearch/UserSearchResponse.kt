package com.slack.exercise.model.usersearch

import kotlinx.serialization.Serializable

/**
 * Models the search query response.
 */
@Serializable
data class UserSearchResponse(val ok: Boolean, val users: List<User>)