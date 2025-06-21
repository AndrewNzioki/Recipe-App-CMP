package org.andrew.recipeappcmp.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.andrew.recipeappcmp.features.common.data.database.DbHelper
import org.andrew.recipeappcmp.features.common.data.database.daos.FavoriteRecipeDao
import org.andrew.recipeappcmp.features.common.data.database.daos.RecipeDao
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

/**
 * Defines a Koin module that provides dependencies for database access and coroutine management.
 *
 * This module includes:
 * - A [CoroutineContext] bound to [Dispatchers.Default] for background operations.
 * - A [CoroutineScope] that uses the provided [CoroutineContext].
 * - A singleton instance of [DbHelper] for managing thread-safe access to the database.
 * - A singleton instance of [RecipeDao] for accessing recipe-related database operations.
 *
 * These dependencies are typically used for caching and data persistence in a shared or platform-specific context.
 *
 * @return A Koin [Module] configured with the required cache-related singletons.
 */
fun cacheModule() = module {
    single<CoroutineContext> { Dispatchers.Default }
    single { CoroutineScope(get()) }

    single { DbHelper(get()) }
    single { RecipeDao(get()) }

    single{ FavoriteRecipeDao(get()) }
}
