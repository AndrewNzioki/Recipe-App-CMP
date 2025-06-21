package org.andrew.recipeappcmp.features.favorites.data

import org.andrew.recipeappcmp.features.common.data.database.daos.FavoriteRecipeDao
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

class FavoriteRecipeLocalDataSourceImpl(
    private val favoriteRecipeDao: FavoriteRecipeDao
): FavoriteRecipeLocalDataSource {
    override suspend fun getAllFavoriteRecipes(): List<RecipeItem> {
        return favoriteRecipeDao.getAllFavoriteRecipes()
    }

    override suspend fun addFavorite(recipeId: Long) {
        return favoriteRecipeDao.addFavorite(recipeId)
    }

    override suspend fun removeFavorite(recipeId: Long) {
        return favoriteRecipeDao.removeFavorite(recipeId)
    }


}