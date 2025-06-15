package org.andrew.recipeappcmp.features.detail.repositories

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

interface RecipeDetailRepository {
    suspend fun getRecipesDetail(id: Long): Result<RecipeItem>
}