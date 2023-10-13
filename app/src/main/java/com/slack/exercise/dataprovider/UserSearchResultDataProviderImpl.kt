package com.slack.exercise.dataprovider

import com.slack.exercise.api.SlackApi
import com.slack.exercise.model.usersearch.User
import com.slack.exercise.model.usersearch.UserSearchResponse
import com.slack.exercise.util.extensions.toNetworkResult
import com.slack.exercise.util.state.NetworkResult
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
     * Returns a [UserSearchResponse] wrapped in a [NetworkResult].
     */
    override suspend fun fetchUsers(searchTerm: String): NetworkResult<UserSearchResponse> {
        return slackApi.searchUsers(searchTerm).toNetworkResult()
    }
}