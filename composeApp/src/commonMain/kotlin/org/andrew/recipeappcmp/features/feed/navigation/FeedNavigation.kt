package org.andrew.recipeappcmp.features.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.andrew.recipeappcmp.features.app.data.Screen
import org.andrew.recipeappcmp.features.feed.ui.FeedRoute

/**
 * Extension function on [NavController] to navigate to the Home/Feed screen.
 *
 * @param navOptions Optional [NavOptions] to customize the navigation behavior, such as
 *                   single top launch or pop behavior. If null, default options are used.
 *
 * Usage:
 * ```
 * navController.navigateToFeed()
 * ```
 */
fun NavController.navigateToFeed(
    navOptions: NavOptions? = null
) {
    navigate(Screen.Home.route)
}

/**
 * Defines the navigation graph for the feed screen.
 *
 * This function should be called within a [NavGraphBuilder] to register the
 * Home/Feed route and associate it with the [FeedRoute] composable.
 *
 * @param navigateToSearch Lambda function that gets triggered when the user initiates
 *                         navigation from the feed to the search screen (e.g. via a button or search bar).
 *
 * Example usage in the main graph:
 * ```
 * navController.feedNavGraph(
 *     navigateToSearch = { navController.navigate(Screen.Search.route) }
 * )
 * ```
 */
fun NavGraphBuilder.feedNavGraph(
    navigateToSearch: () -> Unit
) {
    composable(Screen.Home.route) {
        FeedRoute(
            navigateToSearch = navigateToSearch
        )
    }
}
