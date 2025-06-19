package org.andrew.recipeappcmp.features.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.andrew.recipeappcmp.features.detail.repositories.RecipeDetailRepository

class RecipeDetailViewModel(
    private val recipeDetailRepository: RecipeDetailRepository
): ViewModel() {
    private var _detailUiState = MutableStateFlow(RecipeDetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    suspend fun getRecipeDetail(id: Long){
        viewModelScope.launch {
            val recipeDetailRes = recipeDetailRepository.getRecipesDetail(id)
            when{
                recipeDetailRes.isSuccess -> {
                    _detailUiState.value = _detailUiState.value.copy(
                        recipesDetail = recipeDetailRes.getOrNull(),
                        recipesDetailIsLoading = false
                    )
                }
                recipeDetailRes.isFailure -> {
                    _detailUiState.value = _detailUiState.value.copy(
                        recipesDetailError = recipeDetailRes.exceptionOrNull()?.message,
                        recipesDetailIsLoading = false
                    )
                }
            }
        }
    }
}