package org.andrew.recipeappcmp.features.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.andrew.recipeappcmp.features.app.data.Screen
import org.andrew.recipeappcmp.features.favorites.ui.FavoritesRoute

fun NavController.navigateToFavorites(
    navOptions: NavOptions? = null
) {
    navigate(Screen.Favorites.route)
}

fun NavGraphBuilder.favoritesNavGraph() {
    composable(Screen.Favorites.route) {
        FavoritesRoute()
    }
}
