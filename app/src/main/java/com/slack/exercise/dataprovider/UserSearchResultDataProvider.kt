package com.slack.exercise.dataprovider

import com.slack.exercise.model.usersearch.User
import com.slack.exercise.model.usersearch.UserSearchResult
import com.slack.exercise.util.state.NetworkResult
import com.slack.exercise.util.state.State
import kotlinx.coroutines.flow.Flow

/**
 * Provider of [UserSearchResult].
 * This interface abstracts the logic of searching for users through the API or other data sources.
 */
interface UserSearchResultDataProvider {

  /**
   * Returns a list of [User] wrapped in a [NetworkResult].
   */
  suspend fun fetchUsers(searchTerm: String, forceRefresh: Boolean = false): Flow<List<User>>
}