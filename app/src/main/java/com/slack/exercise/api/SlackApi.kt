package com.slack.exercise.api

import com.slack.exercise.model.usersearch.UserSearchResponse
import retrofit2.Response

/**
 * Interface to the backend API.
 */
interface SlackApi {
  /**
   * Fetches users with name or username matching the [searchTerm].
   * Calling the API passing an empty [searchTerm] fetches the entire team directory.
   *
   * Returns [UserSearchResponse] returned by the API wrapped in a [Response].
   *
   * Operates on a background thread.
   */
  suspend fun searchUsers(searchTerm: String): Response<UserSearchResponse>
}