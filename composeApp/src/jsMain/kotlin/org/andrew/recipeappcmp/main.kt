package org.andrew.recipeappcmp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.andrew.recipeappcmp.di.initKoinJs
import org.jetbrains.skiko.wasm.onWasmReady

val koin = initKoinJs()

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        ComposeViewport(document.body!!) {
            App()
        }
    }

}