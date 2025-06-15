package org.andrew.recipeappcmp.features.detail.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.andrew.recipeappcmp.features.detail.repositories.RecipeDetailRepository

class RecipeDetailViewModel(
    private val recipeDetailRepository: RecipeDetailRepository
): ViewModel() {
    private var _detailUiState = MutableStateFlow(RecipeDetailUiState())
    val detailUiState = _detailUiState.asStateFlow()
}