package org.andrew.recipeappcmp.features.detail.ui

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

data class RecipeDetailUpdateIsFavoriteUiState(
    val isSuccess: Boolean? = null,
    val isUpdating: Boolean = true,
    val error: String? = null,

)

