package org.andrew.recipeappcmp.features.feed.data.datasources

import org.andrew.recipeappcmp.features.common.data.database.daos.RecipeDao
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

class FeedLocalDataSourceImpl(
    private val recipeDao: RecipeDao
): FeedLocalDataSource {
    override suspend fun getRecipesList(): List<RecipeItem>{
       return recipeDao.getAllRecipes()
    }
}