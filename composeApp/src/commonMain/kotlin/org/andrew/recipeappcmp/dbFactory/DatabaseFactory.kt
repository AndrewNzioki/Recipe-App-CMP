package org.andrew.recipeappcmp.dbFactory

import app.cash.sqldelight.db.SqlDriver

/**
 * Expects a platform-specific implementation of a factory for creating a SQL driver.
 *
 * This class abstracts the platform-dependent setup of a [SqlDriver] for SQLDelight,
 * allowing shared code to interact with the database across different platforms
 * such as Android, iOS, or Desktop.
 *
 * Actual implementations in platform-specific source sets must provide a concrete
 * [SqlDriver] to be used with the shared database schema.
 */

// The name of the database file used by all platform-specific drivers.
const val DB_FILE_NAME = "cmpapp.db"

expect class DatabaseFactory {

    /**
     * Creates and returns a [SqlDriver] appropriate for the current platform.
     *
     * This suspend function is implemented per platform to return the correct SQLDelight
     * driver, using the shared schema and database name defined by [DB_FILE_NAME].
     *
     * @return A platform-specific [SqlDriver] instance.
     */
    suspend fun createDriver(): SqlDriver
}
