package com.slack.exercise.ui.usersearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slack.exercise.dataprovider.UserSearchResultDataProvider
import com.slack.exercise.model.usersearch.User
import com.slack.exercise.util.Trie
import com.slack.exercise.util.state.State
import com.slack.exercise.util.state.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * ViewModel responsible for reacting to user inputs and initiating search queries.
 */
@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val userNameResultDataProvider: UserSearchResultDataProvider
) : ViewModel() {

    private var denyList = Trie()

    private val _searchResults: MutableStateFlow<State<List<User>>> = MutableStateFlow(State.Loading)
    val searchResults = _searchResults.asStateFlow()

    private var _searchQuery = MutableStateFlow("")
    val searchQueryState = _searchQuery
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            _searchQuery.value
        )

    init {
        _searchQuery
            .debounce(150)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach {
                if (denyList.contains(searchQueryState.value)) {
                    _searchResults.value = State.Error(IllegalArgumentException("Invalid input entered."))
                } else {
                    fetchSearchQueryResults()
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadDenyList(trie: Trie) {
        denyList = trie
    }

    fun updateSearchQuery(input: String) {
        _searchQuery.value = input
    }
    /**
     * Fetches search results for the current search query.
     */
    fun fetchSearchQueryResults() = viewModelScope.launch {
        userNameResultDataProvider
            .fetchUsers(searchQueryState.value)
            .catch {
                _searchResults.value = State.Error(Exception(it))
            }
            .collect {
                _searchResults.value = State.Success(it)
            }
    }
}