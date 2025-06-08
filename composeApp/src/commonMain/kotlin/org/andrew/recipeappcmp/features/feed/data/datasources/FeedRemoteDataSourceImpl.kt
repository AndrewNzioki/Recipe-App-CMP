package org.andrew.recipeappcmp.features.feed.data.datasources

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.andrew.recipeappcmp.features.common.data.api.BASE_URL
import org.andrew.recipeappcmp.features.common.data.models.RecipeListApiResponse
import org.andrew.recipeappcmp.features.common.data.models.toRecipe
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

class FeedRemoteDataSourceImpl(
    private val httpClient: HttpClient
): FeedRemoteDataSource{
    override suspend fun getRecipesList(): List<RecipeItem>{
        val httpResponse = httpClient.get("{$BASE_URL}search.php?f=b")
        val recipeListApiResponse = httpResponse.body<RecipeListApiResponse>()
        return  recipeListApiResponse.meals.mapNotNull {
            it.toRecipe()
        }
    }
}