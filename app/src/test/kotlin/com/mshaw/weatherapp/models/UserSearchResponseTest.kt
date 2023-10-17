package com.mshaw.weatherapp.models

import com.slack.exercise.model.usersearch.UserSearchResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserSearchResponseTest {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
        explicitNulls = false
        coerceInputValues = true
    }
    private var userSearchResponse: UserSearchResponse? = null

    @OptIn(ExperimentalSerializationApi::class)
    @Before
    fun setUp() {
        userSearchResponse = try {
            val stream = javaClass.getResourceAsStream("/user_search_response_success.json") ?: return
            json.decodeFromStream<UserSearchResponse>(stream)
        } catch (e: Exception) {
            null
        }
    }

    @Test
    fun `test should return ok response`() {
        assertEquals(userSearchResponse?.ok, true)
    }

    @Test
    fun `test should have user response list`() {
        assertEquals(userSearchResponse?.users?.size, 100)
    }
}