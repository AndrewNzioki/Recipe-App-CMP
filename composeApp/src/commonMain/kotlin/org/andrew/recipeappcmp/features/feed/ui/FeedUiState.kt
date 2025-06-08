package org.andrew.recipeappcmp.features.feed.ui

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

data class FeedUiState(
    val recipesList: List<RecipeItem>? = null,
    val recipesListIsLoading: Boolean = true,
    val recipesListError: String? = null
)
