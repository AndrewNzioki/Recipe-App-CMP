package org.andrew.recipeappcmp.features.favorites.ui

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

data class FavoriteScreenUiState(
    val itemsList: List<RecipeItem>? = null,
    val itemsListIsLoading: Boolean = true,
    val itemsListError: String? = null
)
