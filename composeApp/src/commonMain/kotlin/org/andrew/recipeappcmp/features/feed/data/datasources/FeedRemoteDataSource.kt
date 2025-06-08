package org.andrew.recipeappcmp.features.feed.data.datasources

import io.ktor.client.HttpClient
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedRemoteDataSource {
    suspend fun getRecipesList(): List<RecipeItem>
}