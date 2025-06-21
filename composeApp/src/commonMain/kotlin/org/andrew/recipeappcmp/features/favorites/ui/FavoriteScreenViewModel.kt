package org.andrew.recipeappcmp.features.favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.andrew.recipeappcmp.features.favorites.domain.FavoriteRecipeRepository
import org.andrew.recipeappcmp.features.feed.ui.FeedUiState

class FavoriteScreenViewModel(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
): ViewModel() {
    private var _favoriteScreenUiState = MutableStateFlow(FavoriteScreenUiState())
    val favoriteScreenUiState = _favoriteScreenUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getRecipesList()
        }

    }

    private suspend fun getRecipesList(){
        val recipesList = favoriteRecipeRepository.getAllFavoriteRecipes()
        if (recipesList.isSuccess){
            _favoriteScreenUiState.value = _favoriteScreenUiState.value.copy(
                itemsList = recipesList.getOrDefault(emptyList()),
                itemsListIsLoading = false
            )
        }else {
            _favoriteScreenUiState.update {
                it.copy(
                    itemsListError = recipesList.exceptionOrNull()?.message,
                    itemsListIsLoading = false
                )
            }
        }
    }
}