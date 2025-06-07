package org.andrew.recipeappcmp.dbFactory

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.andrew.recipeappcmp.RecipeAppCmpAppDb
import org.w3c.dom.Worker

/**
 * Actual implementation of [DatabaseFactory] for JavaScript (browser) platform.
 *
 * This class sets up a [SqlDriver] using SQLDelight's [WebWorkerDriver], which runs
 * SQLite in a Web Worker using SQL.js. This approach allows non-blocking, performant
 * database operations in browser environments.
 *
 * The driver uses a worker script provided by the SQLDelight SQL.js package.
 * The schema is explicitly created using [RecipeAppCmpAppDb.Schema.awaitCreate] to
 * ensure the database is initialized before use.
 */
actual class DatabaseFactory {

    /**
     * Creates and returns a [SqlDriver] configured for the JavaScript (browser) platform.
     *
     * Initializes a [WebWorkerDriver] using the SQL.js worker script and creates the
     * database schema before returning the driver.
     *
     * @return A [SqlDriver] for browser-based database access using SQLDelight.
     */
    actual suspend fun createDriver(): SqlDriver {
        val driver = WebWorkerDriver(
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            )
        )
        RecipeAppCmpAppDb.Schema.awaitCreate(driver)
        return driver
    }
}
