package com.slack.exercise.ui.usersearch

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.slack.exercise.ui.usersearch.screens.UserSearchScreen

/**
 * Launcher activity. Kept light and simple to delegate view logic to fragment(s) it attaches.
 */
class UserSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserSearchScreen()
        }
    }
}
