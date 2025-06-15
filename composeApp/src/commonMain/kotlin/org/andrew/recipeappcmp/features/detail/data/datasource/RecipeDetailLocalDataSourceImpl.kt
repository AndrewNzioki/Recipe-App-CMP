package org.andrew.recipeappcmp.features.detail.data.datasource

import org.andrew.recipeappcmp.features.common.data.database.daos.RecipeDao
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

class RecipeDetailLocalDataSourceImpl(
    private val recipeDao: RecipeDao
): RecipeDetailLocalDataSource {

    override suspend fun getRecipeDetail(id: Long): RecipeItem?{
        return recipeDao.getRecipeById(id)
    }

    override suspend fun saveRecipe(recipe: RecipeItem) {
        recipeDao.insertRecipe(recipe)
    }
}