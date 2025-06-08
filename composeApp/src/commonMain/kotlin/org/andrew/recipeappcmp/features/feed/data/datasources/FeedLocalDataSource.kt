package org.andrew.recipeappcmp.features.feed.data.datasources

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedLocalDataSource {
    suspend fun getRecipesList(): List<RecipeItem>

    suspend fun saveRecipesList(recipes: List<RecipeItem>)
}