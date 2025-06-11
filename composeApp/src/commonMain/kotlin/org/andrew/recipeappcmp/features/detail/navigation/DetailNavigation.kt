package org.andrew.recipeappcmp.features.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.andrew.recipeappcmp.features.app.data.Screen
import org.andrew.recipeappcmp.features.detail.ui.DetailRoute
import org.andrew.recipeappcmp.features.favorites.ui.FavoritesRoute

fun NavController.navigateToDetail(
    navOptions: NavOptions? = null
) {
    navigate(Screen.Detail.route)
}

fun NavGraphBuilder.detailNavGraph() {
    composable(Screen.Favorites.route) {
        DetailRoute()
    }
}
