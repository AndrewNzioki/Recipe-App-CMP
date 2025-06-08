package org.andrew.recipeappcmp.features.common.data.database.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
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
class RecipeDao(
    private val dbHelper: DbHelper
) {

    /**
     * Inserts a single recipe into the database.
     * If the recipe with the same ID already exists, this will throw a conflict.
     *
     * @param recipeItem The recipe item to be inserted.
     */
    suspend fun insertRecipe(recipeItem: RecipeItem) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.insertRecipe(
                recipeItem.id,
                recipeItem.title,
                recipeItem.description,
                recipeItem.category,
                recipeItem.area,
                recipeItem.imageUrl,
                recipeItem.youtubeLink,
                recipeItem.ingredients,
                recipeItem.instructions,
                isFavorite = if (recipeItem.isFavorite) 1 else 0,
                recipeItem.rating,
                recipeItem.duration,
                recipeItem.difficulty
            )
        }
    }

    /**
     * Updates an existing recipe in the database based on its ID.
     * No-op if the recipe does not exist.
     *
     * @param recipeItem The updated recipe item.
     */
    suspend fun updateRecipe(recipeItem: RecipeItem) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.updateRecipe(
                recipeItem.title,
                recipeItem.description,
                recipeItem.category,
                recipeItem.area,
                recipeItem.imageUrl,
                recipeItem.youtubeLink,
                recipeItem.ingredients,
                recipeItem.instructions,
                isFavorite = if (recipeItem.isFavorite) 1 else 0,
                recipeItem.rating,
                recipeItem.duration,
                recipeItem.difficulty,
                recipeItem.id,
            )
        }
    }

    /**
     * Inserts multiple recipes into the database.
     * If any recipe with the same ID already exists, the insert will be ignored for that entry.
     *
     * @param recipes List of recipes to insert.
     */
    suspend fun insertRecipeBulk(recipes: List<RecipeItem>) {
        dbHelper.withDatabase { database ->
            recipes.forEach { recipeItem ->
                database.recipeEntityQueries.insertRecipe(
                    recipeItem.id,
                    recipeItem.title,
                    recipeItem.description,
                    recipeItem.category,
                    recipeItem.area,
                    recipeItem.imageUrl,
                    recipeItem.youtubeLink,
                    recipeItem.ingredients,
                    recipeItem.instructions,
                    isFavorite = if (recipeItem.isFavorite) 1 else 0,
                    recipeItem.rating,
                    recipeItem.duration,
                    recipeItem.difficulty
                )
            }
        }
    }

    /**
     * Inserts or updates multiple recipes in the database using the upsert strategy.
     * If a recipe already exists (matching ID), it will be updated;
     * otherwise, it will be inserted.
     *
     * @param recipes List of recipes to upsert.
     */
    suspend fun upsertRecipeBulk(recipes: List<RecipeItem>) {
        dbHelper.withDatabase { database ->
            recipes.forEach { recipeItem ->
                database.recipeEntityQueries.upsertRecipe(
                    recipeItem.title,
                    recipeItem.description,
                    recipeItem.category,
                    recipeItem.area,
                    recipeItem.imageUrl,
                    recipeItem.youtubeLink,
                    recipeItem.ingredients,
                    recipeItem.instructions,
                    isFavorite = if (recipeItem.isFavorite) 1 else 0,
                    recipeItem.rating,
                    recipeItem.duration,
                    recipeItem.difficulty,
                    recipeItem.id,
                )
            }
        }
    }

    /**
     * Retrieves all recipes from the database.
     *
     * @return A list of all stored recipe items.
     */
    suspend fun getAllRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { database ->
            database.recipeEntityQueries.selectAllRecipes().awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id The ID of the recipe.
     * @return The recipe item, or null if not found.
     */
    suspend fun getRecipeById(id: Long): RecipeItem? {
        return dbHelper.withDatabase { database ->
            database.recipeEntityQueries.selectRecipeById(id).awaitAsOneOrNull()?.let {
                recipeEntityMapper(it)
            }
        }
    }

    /**
     * Deletes a recipe from the database by its ID.
     *
     * @param id The ID of the recipe to delete.
     */
    suspend fun deleteRecipeById(id: Long) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.deleteRecipeById(id)
        }
    }
}
