package org.andrew.recipeappcmp.features.detail.data.datasource

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

interface RecipeDetailLocalDataSource {

    suspend fun getRecipeDetail(id: Long): RecipeItem?

    suspend fun saveRecipe(recipe: RecipeItem)
}