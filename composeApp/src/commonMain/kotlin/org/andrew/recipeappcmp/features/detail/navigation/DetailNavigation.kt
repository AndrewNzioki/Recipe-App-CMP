package org.andrew.recipeappcmp.features.detail.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.savedstate.read
import org.andrew.recipeappcmp.features.app.data.Screen
import org.andrew.recipeappcmp.features.detail.ui.DetailRoute

import org.koin.core.component.getScopeName

const val RECIPE_ID_ARG = "recipeId"

fun NavController.navigateToDetail(
    id: Long,
    navOptions: NavOptions? = null
) {
    navigate(Screen.Detail.route.replace("$RECIPE_ID_ARG={$RECIPE_ID_ARG}",
        "$RECIPE_ID_ARG=$id"))
}

fun NavGraphBuilder.detailNavGraph(
    onBackClick: () -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit
) {
    composable(Screen.Detail.route,
        arguments = listOf(
            navArgument(RECIPE_ID_ARG){
                type = NavType.LongType
            }
        )
    ) {
        val recipeId = it.arguments?.read { getLong(RECIPE_ID_ARG) } ?: 0
        DetailRoute(
            recipeId = recipeId,
            onBackClick = onBackClick,
            isUserLoggedIn = isUserLoggedIn,
            openLoginBottomSheet = openLoginBottomSheet)
    }
}
