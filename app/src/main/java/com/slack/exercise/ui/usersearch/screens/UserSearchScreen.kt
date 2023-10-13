package com.slack.exercise.ui.usersearch.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.slack.exercise.model.usersearch.User
import com.slack.exercise.ui.usersearch.viewmodel.UserSearchViewModel
import com.slack.exercise.util.state.State

@Composable
fun UserSearchScreen() {
    val viewModel: UserSearchViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchSearchQueryResults()
    }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.searchQuerySubject,
            onValueChange = {
                viewModel.searchQuerySubject = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.fetchSearchQueryResults()
                }
            )
        )

        when (val result = viewModel.searchResults.collectAsStateWithLifecycle().value) {
            State.Loading -> {
                LoadingScreen()
            }

            is State.Success -> {
                UserSearchSuccess(result.value)
            }

            is State.Error -> {
                UserSearchError()
            }
        }
    }
}

@Composable
fun UserSearchSuccess(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            UserSearchItem(user)
        }
    }
}

@Composable
fun UserSearchItem(user: User) {

}

@Composable
fun UserSearchError() {
    Column {
        Icon(Icons.Default.Warning, contentDescription = null)
        Text("Sorry, there was an error while loading data.")
    }
}