package org.andrew.recipeappcmp.features.feed.data.repositories

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem
import org.andrew.recipeappcmp.features.feed.data.datasources.FeedLocalDataSource
import org.andrew.recipeappcmp.features.feed.data.datasources.FeedRemoteDataSource
import org.andrew.recipeappcmp.features.feed.domain.repositories.FeedRepository

/**
 * Implementation of the [FeedRepository] interface that manages fetching recipes
 * from local and remote data sources.
 *
 * This class uses a cache-first strategy:
 * - It first tries to retrieve recipes from the local data source.
 * - If the local cache is empty, it fetches the recipes from the remote data source
 *   and stores them locally for future use.
 *
 * @property feedLocalDataSource The local data source used for caching recipe data.
 * @property feedRemoteDataSource The remote data source used for fetching recipe data from the network.
 */
class FeedRepositoryImpl(
    private val feedLocalDataSource: FeedLocalDataSource,
    private val feedRemoteDataSource: FeedRemoteDataSource
) : FeedRepository {

    /**
     * Retrieves a list of recipes.
     *
     * The method first checks the local cache. If there are any cached recipes,
     * it returns them immediately. Otherwise, it fetches the list from the remote
     * data source, saves it locally, and then returns the result.
     *
     * All operations are wrapped in a [Result] to handle both success and failure cases.
     *
     * @return A [Result] containing either the list of [RecipeItem] on success,
     *         or an exception on failure.
     */
    override suspend fun getRecipesList(): Result<List<RecipeItem>> {
        return try {
            val recipeListCache = feedLocalDataSource.getRecipesList()
            val count = recipeListCache.count()
            if (count > 0) {
                Result.success(recipeListCache)
            } else {
                val recipeListApiResponse = feedRemoteDataSource.getRecipesList()
                feedLocalDataSource.saveRecipesList(recipeListApiResponse)
                Result.success(recipeListApiResponse)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
