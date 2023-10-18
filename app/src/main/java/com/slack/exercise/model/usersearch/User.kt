package com.slack.exercise.model.usersearch

import com.slack.exercise.dao.entity.UserEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * User model returned by the API.
 */
@Serializable
data class User(
    val username: String,
    @SerialName("display_name")
    val displayName: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)

fun User.toUserEntity(): UserEntity {
    return UserEntity(username, displayName, avatarUrl)
}