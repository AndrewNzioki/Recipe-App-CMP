package org.andrew.recipeappcmp.features.feed.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedRoute(
    navigateToSearch: () -> Unit,
    feedViewModel: FeedViewModel = koinViewModel()
){
    val feedUiState = feedViewModel.feedUiState.collectAsStateWithLifecycle()

    FeedScreen(
        feedUiState = feedUiState.value,
        navigateToSearch = navigateToSearch
    )
}


@Composable
fun FeedScreen(
    feedUiState: FeedUiState,
    navigateToSearch: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Feed Screen")
    }
}