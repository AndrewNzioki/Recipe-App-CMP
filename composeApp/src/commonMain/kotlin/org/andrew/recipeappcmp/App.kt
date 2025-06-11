package org.andrew.recipeappcmp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.andrew.recipeappcmp.features.app.data.rememberAppState
import org.andrew.recipeappcmp.features.app.navigation.AppNavHost
import org.andrew.recipeappcmp.features.designSystem.theme.RecipeAppCmpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.core.context.KoinContext

@Composable
@Preview
fun App() {
    RecipeAppCmpTheme {

        KoinContext {
            val navController = rememberNavController()

            val appState = rememberAppState(navController)
            AppNavHost(
                appState = appState
            )
        }


    }
}