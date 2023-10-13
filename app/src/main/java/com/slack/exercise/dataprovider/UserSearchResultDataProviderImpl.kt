package com.slack.exercise.dataprovider

import com.slack.exercise.api.SlackApi
import com.slack.exercise.model.usersearch.User
import com.slack.exercise.model.usersearch.UserSearchResponse
import com.slack.exercise.util.extensions.toNetworkResult
import com.slack.exercise.util.state.NetworkResult
import com.slack.exercise.util.state.transform
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [UserSearchResultDataProvider].
 */
@Singleton
class UserSearchResultDataProviderImpl @Inject constructor(
    private val slackApi: SlackApi
) : UserSearchResultDataProvider {

    /**
     * Returns a list of [User] wrapped in a [NetworkResult].
     */
    override suspend fun fetchUsers(searchTerm: String): NetworkResult<List<User>> {
        return slackApi.searchUsers(searchTerm)
            .toNetworkResult()
            .transform {
                it.users
            }
    }
}