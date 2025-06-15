package org.andrew.recipeappcmp.features.detail.repositories

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem
import org.andrew.recipeappcmp.features.detail.data.datasource.RecipeDetailLocalDataSource
import org.andrew.recipeappcmp.features.detail.data.datasource.RecipeDetailRemoteDataSource

class RecipeDetailRepositoryImpl(
    private val recipeDetailLocalDataSource: RecipeDetailLocalDataSource,
    private val recipeDetailRemoteDataSource: RecipeDetailRemoteDataSource
) : RecipeDetailRepository {
    override suspend fun getRecipesDetail(id: Long): Result<RecipeItem> {

        return try {
            val recipeDetailCache = recipeDetailLocalDataSource.getRecipeDetail(id)
            val count = recipeDetailCache
            if (recipeDetailCache != null) {
                Result.success(recipeDetailCache)
            } else {
                val recipeDetailApiResponse = recipeDetailRemoteDataSource.getRecipeDetail(id)
                    ?: return Result.failure(Exception("Recipe Not Found!"))

                recipeDetailLocalDataSource.saveRecipe(recipeDetailApiResponse)
                Result.success(recipeDetailApiResponse)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}