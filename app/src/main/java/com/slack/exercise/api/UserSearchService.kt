package com.slack.exercise.api

import com.slack.exercise.model.usersearch.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserSearchService {
  /**
   * Search query. Returns an API response wrapped in a [Response].
   */
  @GET("search")
  suspend fun searchUsers(@Query("query") query: String): Response<UserSearchResponse>
}
