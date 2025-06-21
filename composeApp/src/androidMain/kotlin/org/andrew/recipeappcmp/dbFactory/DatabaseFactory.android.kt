package org.andrew.recipeappcmp.dbFactory

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.andrew.recipeappcmp.RecipeAppCmpAppDb

/**
 * Platform-specific implementation of a factory class for creating a SQL driver on Android.
 *
 * This class provides the Android-specific `SqlDriver` needed by SQLDelight to interface
 * with a local SQLite database.
 *
 * @param context The Android [Context], typically the application context, required to
 *                create the database driver.
 */

actual class DatabaseFactory(
    private val context: Context
) {

    /**
     * Creates and returns an instance of [SqlDriver] for Android using [AndroidSqliteDriver].
     *
     * This driver is configured with the application's SQLDelight database schema and
     * connects to a local SQLite database.
     *
     * @return A [SqlDriver] instance for database access on Android.
     */
    actual suspend fun createDriver(): SqlDriver {

        val schema = RecipeAppCmpAppDb.Schema.synchronous()
        return AndroidSqliteDriver(
            schema, context = context, DB_FILE_NAME,
            callback = object : AndroidSqliteDriver.Callback(schema){
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
    }
}
