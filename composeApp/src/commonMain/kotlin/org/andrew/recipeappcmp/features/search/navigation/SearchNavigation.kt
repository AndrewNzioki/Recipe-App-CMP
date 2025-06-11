package org.andrew.recipeappcmp.features.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.andrew.recipeappcmp.features.app.data.Screen
import org.andrew.recipeappcmp.features.search.ui.SearchRoute

fun NavController.navigateToSearch(
    navOptions: NavOptions? = null
) {
    navigate(Screen.Search.route)
}

fun NavGraphBuilder.searchNavGraph() {
    composable(Screen.Favorites.route) {
        SearchRoute()
    }
}
