package com.mshaw.weatherapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import coil.request.Disposable
import com.slack.exercise.dataprovider.UserSearchResultDataProviderImpl
import com.slack.exercise.model.usersearch.UserSearchResponse
import com.slack.exercise.ui.usersearch.viewmodel.UserSearchViewModel
import com.slack.exercise.util.state.NetworkResult
import com.slack.exercise.util.state.State
import com.slack.exercise.util.state.transform
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkConstructor
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.Response
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    val testScheduler = TestCoroutineScheduler()
    val testDispatcher = StandardTestDispatcher(testScheduler)
    val testScope = TestScope(testDispatcher)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var response: Response

    @MockK
    lateinit var userSearchResultDataProvider: UserSearchResultDataProviderImpl

    @MockK
    lateinit var viewModel: UserSearchViewModel

    private var successfulResponse: UserSearchResponse? = null
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
        explicitNulls = false
        coerceInputValues = true
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        mockkConstructor(Disposable::class)
        mockkConstructor(State.Success::class)
        mockkConstructor(State.Error::class)

        successfulResponse = try {
            val stream = javaClass.getResourceAsStream("/user_search_response_success.json") ?: return
            json.decodeFromStream<UserSearchResponse>(stream)
        } catch (e: Exception) {
            null
        }
    }

    @Test
    fun shouldParseJsonAsResponse() = testScope.runTest {
        val successfulResponse = successfulResponse
        if (successfulResponse == null) {
            fail("successResponse is null")
        }

        assertEquals(successfulResponse?.ok, true)
    }

    @Test
    fun shouldEmitSuccessState() = testScope.runTest {
        val successfulResponse = successfulResponse
        if (successfulResponse == null) {
            fail("successResponse is null")
            return@runTest
        }

        every { viewModel.fetchSearchQueryResults() } answers { Job() }
        coEvery { response.isSuccessful } answers { true }
        coEvery { userSearchResultDataProvider.fetchUsers(any()) } returns NetworkResult.Ok(successfulResponse, response).transform { it.users }
        advanceUntilIdle()
        viewModel.fetchSearchQueryResults()
        verify { viewModel.fetchSearchQueryResults() }
        confirmVerified(State.Success(successfulResponse))
    }

    @Test
    fun shouldEmitErrorState() = testScope.runTest {
        val exception = Exception()

        every { viewModel.fetchSearchQueryResults() } answers { Job() }
        coEvery { response.isSuccessful } answers { true }
        coEvery { userSearchResultDataProvider.fetchUsers(any()) } returns NetworkResult.Error(exception)
        advanceUntilIdle()
        viewModel.fetchSearchQueryResults()
        verify { viewModel.fetchSearchQueryResults() }
        confirmVerified(State.Error(exception))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}