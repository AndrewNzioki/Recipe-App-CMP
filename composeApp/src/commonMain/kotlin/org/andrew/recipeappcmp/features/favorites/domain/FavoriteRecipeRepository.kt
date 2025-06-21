package org.andrew.recipeappcmp.features.favorites.domain

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

interface FavoriteRecipeRepository {
    suspend fun getAllFavoriteRecipes(): Result<List<RecipeItem>>

    suspend fun addFavorite(recipeId: Long)

    suspend fun removeFavorite(recipeId: Long)
}