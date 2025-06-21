package org.andrew.recipeappcmp.features.common.data.database.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.andrew.recipeappcmp.features.common.data.database.DbHelper
import org.andrew.recipeappcmp.features.common.data.database.recipeEntityMapper
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

/**
 * Data Access Object (DAO) class for managing recipe-related operations
 * on the local SQLDelight database.
 *
 * This class provides methods to insert, update, upsert, retrieve, and delete recipes
 * using suspend functions to support coroutines for asynchronous database operations.
 *
 * @property dbHelper Provides access to the SQLDelight database instance via dependency injection.
 */
class FavoriteRecipeDao(
    private val dbHelper: DbHelper
) {

    /**
     * Inserts a single recipe into the database.
     * If the recipe with the same ID already exists, this will throw a conflict.
     *
     * @param recipeItem The recipe item to be inserted.
     */
    suspend fun addFavorite(recipeId: Long) {

        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.upsertFavorite(
                recipe_id = recipeId,
                added_at = currentDateTime.toString()
            )
        }
    }

    suspend fun removeFavorite(recipeId: Long) {
        dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.deleteFavoriteByRecipeId(
                recipe_id = recipeId
            )
        }
    }

    suspend fun getAllFavoriteRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.selectAllFavoritesRecipes()
                .awaitAsList()
                .map {
                    recipeEntityMapper(it)
                }

        }
    }

    suspend fun isFavorite(recipeId: Long): Boolean {
        return dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.selectFavoriteByRecipeId(
                recipe_id = recipeId
            )
                .awaitAsOneOrNull() != null
        }
    }
}