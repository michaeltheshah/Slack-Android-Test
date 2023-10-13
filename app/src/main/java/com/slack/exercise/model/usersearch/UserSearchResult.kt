package com.slack.exercise.model.usersearch

import kotlinx.serialization.Serializable

/**
 * Models users returned by the API.
 */
@Serializable
data class UserSearchResult(val username: String)