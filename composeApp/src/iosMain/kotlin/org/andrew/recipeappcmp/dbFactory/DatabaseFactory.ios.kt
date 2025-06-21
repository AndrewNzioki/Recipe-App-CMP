package org.andrew.recipeappcmp.dbFactory


import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import org.andrew.recipeappcmp.RecipeAppCmpAppDb



/**
 * Actual implementation of [DatabaseFactory] for native platforms (e.g., iOS, macOS).
 *
 * This class provides a platform-specific [SqlDriver] using SQLDelight's [NativeSqliteDriver]
 * to interface with a local SQLite database.
 *
 * The database file name is defined by the shared constant [DB_FILE_NAME].
 */
actual class DatabaseFactory {

    /**
     * Creates and returns a [SqlDriver] for native platforms using [NativeSqliteDriver].
     *
     * This driver is configured with the SQLDelight schema and connects to a local SQLite
     * database file named [DB_FILE_NAME].
     *
     * @return A [SqlDriver] instance for database access on native platforms.
     */
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            RecipeAppCmpAppDb.Schema.synchronous(), DB_FILE_NAME,
            onConfiguration = {
                it.copy(
                    extendedConfig = DatabaseConfiguration.Extended(
                        foreignKeyConstraints = true
                    )
                )
            }
        )
    }
}
