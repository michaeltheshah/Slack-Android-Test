## Test Slack Android App

This is a basic test Slack Android app that fetches a list of users and presents them in a Jetpack Compose list. It follows
the MVVM architecture and uses Retrofit to fetch data from the Slack API. It also uses Hilt for dependency injection and
Coil for image loading. 

Jetpack Compose is now the official UI toolkit for Android. This implementation of the Slack Test replaces usages of XML
layouts with Composables. In addition, the new Gradle version catalog has been implemented to allow for easier dependency
versioning optimization.

**Features:**

* Fetches a list of users from the Slack API
* Displays the list of users in a Jetpack Compose list
* Allows users to search for users by name.

**Libraries:**

* Retrofit
* OkHttp
* Hilt
* Coil
* Jetpack Compose
* Coroutines
* Flow
* Room

**Architecture:**

* MVVM

**Prerequisites:**

* Android Studio
* Jetpack Compose

**Installation:**

1. Clone this repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on your device or emulator.

**Usage:**

1. Open the app.
2. A list of users will be displayed.
3. Type in search bar to filter users.

**Troubleshooting:**

If you are having any trouble with the app, please open an issue on this repository.

**License:**

This app is licensed under the MIT License.
