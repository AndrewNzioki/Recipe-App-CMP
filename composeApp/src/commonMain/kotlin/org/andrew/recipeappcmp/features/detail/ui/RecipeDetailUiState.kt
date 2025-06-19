package org.andrew.recipeappcmp.features.detail.ui

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

data class RecipeDetailUiState(
    val recipesDetail: RecipeItem? = null,
    val recipesDetailIsLoading: Boolean = true,
    val recipesDetailError: String? = null
)

