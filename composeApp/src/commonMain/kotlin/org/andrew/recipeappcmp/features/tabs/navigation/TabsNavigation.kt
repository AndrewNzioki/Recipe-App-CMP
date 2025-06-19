package org.andrew.recipeappcmp.features.tabs.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.andrew.recipeappcmp.features.app.data.Screen
import org.andrew.recipeappcmp.features.tabs.ui.TabsRoute

fun NavController.navigateToTabs(
    navOptions: NavOptions? = null
) {
    navigate(Screen.Tabs.route)
}

fun NavGraphBuilder.tabsNavGraph(
    navigateToDetail: (Long) -> Unit,
    tabNavController: NavHostController
) {
    composable(Screen.Tabs.route) {
        TabsRoute(
            tabNavController = tabNavController,
            navigateToDetail = navigateToDetail
            )
    }
}
