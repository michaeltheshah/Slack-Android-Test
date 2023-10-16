package com.slack.exercise.ui.usersearch.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.slack.exercise.model.usersearch.User
import com.slack.exercise.theme.avatarRadius
import com.slack.exercise.theme.avatarSize
import com.slack.exercise.theme.displayName
import com.slack.exercise.theme.username
import com.slack.exercise.ui.usersearch.viewmodel.UserSearchViewModel
import com.slack.exercise.util.state.State

@Composable
fun UserSearchScreen() {
    val viewModel: UserSearchViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchSearchQueryResults()
    }

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.searchQuerySubject,
            placeholder = {
                Text(text = "Search...")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            },
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
    Row(
        Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar image
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "avatar",
            modifier = Modifier
                .clip(RoundedCornerShape(avatarRadius))
                .size(avatarSize)
                .aspectRatio(1f)
                .padding(start = 8.dp)
        )

        // Display name text
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = user.displayName,
            style = displayName,
        )

        // Username text
        Text(modifier = Modifier.padding(horizontal = 8.dp),
            text = user.username,
            style = username,
        )
    }
}

@Composable
fun UserSearchError() {
    Column {
        Icon(
            imageVector = Icons.Default.Warning,
            modifier = Modifier.padding(bottom = 4.dp),
            contentDescription = null
        )
        Text("Sorry, there was an error while loading data.")
    }
}