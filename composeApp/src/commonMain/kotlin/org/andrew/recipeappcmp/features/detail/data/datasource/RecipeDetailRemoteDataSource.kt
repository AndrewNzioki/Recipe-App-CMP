package org.andrew.recipeappcmp.features.detail.data.datasource

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

interface RecipeDetailRemoteDataSource {

    suspend fun getRecipeDetail(id: Long): RecipeItem?
}