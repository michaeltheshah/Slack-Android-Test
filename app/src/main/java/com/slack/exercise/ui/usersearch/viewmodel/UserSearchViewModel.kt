package com.slack.exercise.ui.usersearch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slack.exercise.dataprovider.UserSearchResultDataProvider
import com.slack.exercise.model.usersearch.User
import com.slack.exercise.util.state.State
import com.slack.exercise.util.state.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for reacting to user inputs and initiating search queries.
 */
@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val userNameResultDataProvider: UserSearchResultDataProvider
) : ViewModel() {

    private val _searchResults: MutableStateFlow<State<List<User>>> = MutableStateFlow(State.Loading)
    val searchResults = _searchResults.asStateFlow()

    var searchQuerySubject by mutableStateOf("")

    /**
     * Fetches search results for the current search query.
     */
    fun fetchSearchQueryResults() = viewModelScope.launch {
        _searchResults.value = userNameResultDataProvider
            .fetchUsers(searchQuerySubject)
            .toState()
    }
}