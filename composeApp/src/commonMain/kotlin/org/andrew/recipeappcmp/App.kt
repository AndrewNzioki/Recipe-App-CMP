package org.andrew.recipeappcmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import org.andrew.recipeappcmp.features.app.data.rememberAppState
import org.andrew.recipeappcmp.features.app.navigation.AppNavHost
import org.andrew.recipeappcmp.features.designSystem.theme.RecipeAppCmpTheme
import org.andrew.recipeappcmp.features.login.ui.LoginScreenModalBottomSheet
import org.andrew.recipeappcmp.features.login.ui.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.context.KoinContext

@Composable
@Preview
fun App(
    loginViewModel: LoginViewModel = koinViewModel()
) {
    RecipeAppCmpTheme {

        KoinContext {
            val navController = rememberNavController()

            var showLoginBottomSheet by remember {
                mutableStateOf(false)
            }

            val appState = rememberAppState(
                navController,
                scope = rememberCoroutineScope(),
                appPreferences = koinInject()
            )

            val isLoggedIn by appState.isLoggedIn.collectAsStateWithLifecycle()

            val isUserLoggedIn: () -> Boolean = {
                isLoggedIn
            }

            var loginCallback: () -> Unit by remember {
                mutableStateOf({})
            }

            val openLoginBottomSheet : (() -> Unit) -> Unit = { callback ->
                showLoginBottomSheet = true
                loginCallback = callback
            }

            val onLoginSuccess: () -> Unit = {
                showLoginBottomSheet = false
                appState.updateIsLoggedIn(true)
                loginViewModel.resetState()
                loginCallback()
            }

            val onLogout: () -> Unit = {
                appState.onLogout()
                loginViewModel.resetState()
            }

            val onCloseSheet: () -> Unit = {
                appState.onLogout()
                loginViewModel.resetState()
            }

            LoginScreenModalBottomSheet(
                loginViewModel = loginViewModel,
                showBottomSheet = showLoginBottomSheet,
                onClose = onCloseSheet,
                onLoginSuccess = onLoginSuccess
            )


            AppNavHost(
                appState = appState,
                isUserLoggedIn = isUserLoggedIn,
                openLoginBottomSheet = openLoginBottomSheet,
                onLogout = onLogout
            )
        }


    }
}