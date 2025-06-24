package org.andrew.recipeappcmp.features.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.andrew.recipeappcmp.features.detail.repositories.RecipeDetailRepository
import org.andrew.recipeappcmp.features.favorites.domain.FavoriteRecipeRepository

class RecipeDetailViewModel(
    private val recipeDetailRepository: RecipeDetailRepository,
    private val favoriteRecipeRepository: FavoriteRecipeRepository
): ViewModel() {
    private var _detailUiState = MutableStateFlow(RecipeDetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    private var _updateIsFavoriteUiState = MutableStateFlow(RecipeDetailUpdateIsFavoriteUiState())
    val updateIsFavoriteUiState = _updateIsFavoriteUiState.asStateFlow()

    fun getRecipeDetail(id: Long){
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

    fun updateIsFavorite(
        recipeId: Long,
        isAdding: Boolean
    ) {
        viewModelScope.launch {
            try {
                _updateIsFavoriteUiState.value = _updateIsFavoriteUiState.value.copy(
                    isUpdating = true
                )
                if (isAdding){
                    println("Adding Favorite from viewmodel")
                    favoriteRecipeRepository.addFavorite(recipeId)

                }else{
                    favoriteRecipeRepository.removeFavorite(recipeId)
                }

                //refresh detail
                _detailUiState.value = _detailUiState.value.copy(
                    recipesDetail = _detailUiState.value.recipesDetail?.copy(
                        isFavorite = isAdding
                    )
                )
                _updateIsFavoriteUiState.value = _updateIsFavoriteUiState.value.copy(
                    isSuccess = false,
                    isUpdating = false
                )
            } catch (e: Exception) {
                _updateIsFavoriteUiState.value = _updateIsFavoriteUiState.value.copy(
                    error = e.message,
                    isUpdating = false
                )
            }
        }

    }
}