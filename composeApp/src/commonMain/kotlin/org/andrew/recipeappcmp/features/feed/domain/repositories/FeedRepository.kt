package org.andrew.recipeappcmp.features.feed.domain.repositories

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedRepository {
    suspend fun getRecipesList(): Result<List<RecipeItem>>
}