package com.slack.exercise.dataprovider

import com.slack.exercise.api.UserSearchService
import com.slack.exercise.dao.UserDao
import com.slack.exercise.dao.entity.toUser
import com.slack.exercise.model.usersearch.User
import com.slack.exercise.model.usersearch.toUserEntity
import com.slack.exercise.util.extensions.toNetworkResult
import com.slack.exercise.util.extensions.value
import com.slack.exercise.util.state.NetworkResult
import com.slack.exercise.util.state.NetworkResult.Ok
import com.slack.exercise.util.state.State
import com.slack.exercise.util.state.toState
import com.slack.exercise.util.state.transform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [UserSearchResultDataProvider].
 */
@Singleton
class UserSearchResultDataProviderImpl @Inject constructor(
    private val userSearchService: UserSearchService,
    private val userDao: UserDao,
) : UserSearchResultDataProvider {

    /**
     * Emits a list of [User] wrapped in a [NetworkResult] using Flow.
     */
    override suspend fun fetchUsers(searchTerm: String, forceRefresh: Boolean) = flow {
        val localUsers = userDao.getAllUsers()
        val listUsers = if (localUsers.isNotEmpty()) {
            localUsers.map { it.toUser() }
        } else {
            userSearchService.searchUsers(searchTerm).body()!!.users
        }

        emit(listUsers)

        if (forceRefresh) {
            userDao.clearAllUsers()
            userDao.insertUsers(listUsers.map { it.toUserEntity() })
        }
    }
}