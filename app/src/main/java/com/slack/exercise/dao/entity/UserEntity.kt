package com.slack.exercise.dao.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.slack.exercise.model.usersearch.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * User model returned by the API.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val username: String,
    val displayName: String,
    val avatarUrl: String
)

fun UserEntity.toUser(): User {
    return User(username, displayName, avatarUrl)
}