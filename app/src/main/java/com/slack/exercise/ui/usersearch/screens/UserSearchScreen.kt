package com.slack.exercise.ui.usersearch.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.slack.exercise.R
import com.slack.exercise.model.usersearch.User
import com.slack.exercise.theme.avatarRadius
import com.slack.exercise.theme.avatarSize
import com.slack.exercise.theme.displayName
import com.slack.exercise.theme.dividerColor
import com.slack.exercise.theme.dividerHeight
import com.slack.exercise.theme.username
import com.slack.exercise.ui.usersearch.viewmodel.UserSearchViewModel
import com.slack.exercise.util.extensions.toStringList
import com.slack.exercise.util.extensions.toTrie
import com.slack.exercise.util.state.State
import java.lang.Exception

@Composable
fun UserSearchScreen() {
	val viewModel: UserSearchViewModel = hiltViewModel()

	val denylistfile = LocalContext.current.resources.openRawResource(R.raw.denylist)
	val denyTrie = denylistfile.toStringList().toTrie()

	// Using collectAsStateWithLifecycle due to StateFlow not being integrated with
	// the Activity lifecycle like LiveData is.
	val searchQuery by viewModel.searchQueryState.collectAsStateWithLifecycle()

	LaunchedEffect(Unit) {
		viewModel.loadDenyList(denyTrie)
		viewModel.fetchSearchQueryResults()
	}

	Column {
		OutlinedTextField(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp),
			value = searchQuery,
			placeholder = {
				Text(text = stringResource(R.string.search))
			},
			leadingIcon = {
				Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search_content_desc))
			},
			onValueChange = { value ->
				viewModel.updateSearchQuery(value)
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
				UserSearchError(result.exception)
			}
		}
	}
}

@Composable
fun UserSearchSuccess(users: List<User>) {
	LazyColumn {
		itemsIndexed(users) { index, user ->
			UserSearchItem(user)

			if (index < users.lastIndex) {
				Divider(
					modifier = Modifier
						.fillMaxWidth()
						.padding(start = 8.dp),
					thickness = dividerHeight,
					color = dividerColor,
				)
			}
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
			contentDescription = stringResource(R.string.avatar_content_desc),
			modifier = Modifier
				.clip(RoundedCornerShape(avatarRadius))
				.size(avatarSize)
				.aspectRatio(1f)
		)

		// Display name text
		Text(
			modifier = Modifier.padding(start = 12.dp),
			text = user.displayName,
			style = displayName,
		)

		// Username text
		Text(
			modifier = Modifier.padding(horizontal = 8.dp),
			text = user.username,
			style = username,
		)
	}
}

@Composable
fun UserSearchError(exception: Exception) {
	Column(Modifier.padding(8.dp)) {
		Icon(
			imageVector = Icons.Default.Warning,
			modifier = Modifier.padding(bottom = 4.dp),
			contentDescription = null
		)
		Text(exception.localizedMessage ?: stringResource(R.string.generic_error_message))
	}
}