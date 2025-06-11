package org.andrew.recipeappcmp.features.app.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import org.andrew.recipeappcmp.features.tabs.navigation.navigateToTabs

/**
 * Remembers and provides an instance of [AppState] that is tied to the given [navController]
 * and a coroutine [scope]. This helps retain navigation and coroutine-related state
 * across recompositions in a Compose application.
 *
 * @param navController The [NavHostController] used for navigating between destinations.
 * @param scope The [CoroutineScope] used for launching coroutines. Defaults to [rememberCoroutineScope()].
 * @return A remembered [AppState] instance.
 */
@Composable
fun rememberAppState(
    navController: NavHostController,
    scope: CoroutineScope = rememberCoroutineScope()
): AppState {
    return remember(
        navController,
        scope
    ) {
        AppState(
            navController,
            scope
        )
    }
}


/**
 * Holds the core application state, including navigation logic.
 *
 * This class is designed to encapsulate navigation-related actions and
 * coroutine scope references that can be shared across composables.
 *
 * @param navController The [NavHostController] for handling app navigation.
 * @param scope A [CoroutineScope] tied to this state, useful for launching tasks
 *              within the context of the application state.
 */
@Stable
class AppState(
    val navController: NavHostController,
    scope: CoroutineScope
) {
    /**
     * Navigates to the Tabs screen using an extension function on [NavHostController].
     */
    fun navigateToTabs() = navController.navigateToTabs()
}
