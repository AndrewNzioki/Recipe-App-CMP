package org.andrew.recipeappcmp.features.favorites.domain

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem
import org.andrew.recipeappcmp.features.favorites.data.FavoriteRecipeLocalDataSource

class FavoriteRecipeRepositoryImpl(
    private val favoriteRecipeLocalDataSource: FavoriteRecipeLocalDataSource
): FavoriteRecipeRepository {
    override suspend fun getAllFavoriteRecipes(): Result<List<RecipeItem>> {
       return try{
           val list = favoriteRecipeLocalDataSource.getAllFavoriteRecipes()
           Result.success(list)
       }catch (e: Exception){
           Result.failure(e)
       }
    }

    override suspend fun addFavorite(recipeId: Long) {
        print("Adding Favorite from Repo")
        favoriteRecipeLocalDataSource.addFavorite(recipeId)
    }

    override suspend fun removeFavorite(recipeId: Long) {
        favoriteRecipeLocalDataSource.removeFavorite(recipeId)
    }

}