package com.slack.exercise.dataprovider

import com.slack.exercise.model.usersearch.User
import com.slack.exercise.model.usersearch.UserSearchResponse
import com.slack.exercise.model.usersearch.UserSearchResult
import com.slack.exercise.util.state.NetworkResult

/**
 * Provider of [UserSearchResult].
 * This interface abstracts the logic of searching for users through the API or other data sources.
 */
interface UserSearchResultDataProvider {

  /**
   * Returns a list of [User] wrapped in a [NetworkResult].
   */
  suspend fun fetchUsers(searchTerm: String): NetworkResult<List<User>>
}