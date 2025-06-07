package org.andrew.recipeappcmp.features.common.data.database

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.andrew.recipeappcmp.RecipeAppCmpAppDb
import org.andrew.recipeappcmp.dbFactory.DatabaseFactory

/**
 * A helper class that manages access to the [RecipeAppCmpAppDb] database in a thread-safe way.
 *
 * This class ensures the database is lazily initialized only once and provides
 * safe concurrent access using a [Mutex]. It allows database operations to be
 * executed inside a coroutine-safe block using [withDatabase].
 *
 * @property driverFactory A platform-specific [DatabaseFactory] used to create the [SqlDriver] for the database.
 */
class DbHelper(
    private val driverFactory: DatabaseFactory
) {
    // Lazily-initialized database instance
    private var db: RecipeAppCmpAppDb? = null

    // Mutex to guard database initialization and access
    private val mutex = Mutex()

    /**
     * Executes the given [block] with the [RecipeAppCmpAppDb] instance in a thread-safe manner.
     *
     * If the database has not been initialized yet, this function creates it using the provided [driverFactory].
     *
     * @param block A suspend function that receives the database instance and returns a result of type [Result].
     * @return The result of executing [block] with the initialized database.
     */
    suspend fun <Result : Any> withDatabase(block: suspend (RecipeAppCmpAppDb) -> Result) = mutex.withLock {
        if (db == null) {
            db = createDb(driverFactory)
        }

        return@withLock block(db!!)
    }

    /**
     * Initializes and returns a new instance of [RecipeAppCmpAppDb] using the given [driverFactory].
     *
     * @param driverFactory The factory to create a platform-specific SQL driver.
     * @return A new [RecipeAppCmpAppDb] instance.
     */
    private suspend fun createDb(driverFactory: DatabaseFactory): RecipeAppCmpAppDb {
        return RecipeAppCmpAppDb(driver = driverFactory.createDriver())
    }
}
