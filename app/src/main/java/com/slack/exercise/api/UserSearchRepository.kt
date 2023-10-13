package com.slack.exercise.api

import com.slack.exercise.model.usersearch.UserSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [UserSearchRepository] using [UserSearchService] to perform the API requests.
 */
@Singleton
class UserSearchRepository @Inject constructor(retrofit: Retrofit) : UserSearchService {
    private val service = retrofit.create(UserSearchService::class.java)
    override suspend fun searchUsers(searchTerm: String): Response<UserSearchResponse> {
        return withContext(Dispatchers.IO) {
            service.searchUsers(searchTerm)
        }
    }
}
