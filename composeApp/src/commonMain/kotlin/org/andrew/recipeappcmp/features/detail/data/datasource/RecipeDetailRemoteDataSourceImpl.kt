package org.andrew.recipeappcmp.features.detail.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.andrew.recipeappcmp.features.common.data.api.BASE_URL
import org.andrew.recipeappcmp.features.common.data.models.RecipeListApiResponse
import org.andrew.recipeappcmp.features.common.data.models.toRecipe
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

class RecipeDetailRemoteDataSourceImpl(
    private val httpClient: HttpClient
): RecipeDetailRemoteDataSource {

    override suspend fun getRecipeDetail(id: Long): RecipeItem?{
        val httpResponse = httpClient.get("${BASE_URL}lookup.php?i=$id")
        return httpResponse.body<RecipeListApiResponse>().meals.firstOrNull()?.toRecipe()
    }
}