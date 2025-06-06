package org.andrew.recipeappcmp.features.common.data.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Creates and configures an instance of [HttpClient] using Ktor.
 *
 * This HTTP client is set up with the following features:
 *
 * - **ContentNegotiation**:
 *   - Installs support for JSON content using Kotlinx Serialization.
 *   - Configures the JSON parser to:
 *     - `prettyPrint`: Format the output JSON for readability.
 *     - `ignoreUnknownKeys`: Ignore unknown keys in the JSON response to prevent parsing errors.
 *
 * - **Logging**:
 *   - Enables logging of all HTTP request and response details.
 *   - Uses the default logger and logs at the `ALL` level.
 *   - Sanitizes the `Authorization` header in logs to avoid exposing sensitive information.
 *
 * This client can be used for making HTTP requests with built-in JSON parsing and detailed logging.
 *
 * Example usage:
 * ```
 * val response = httpClient.get("https://api.example.com/data")
 * ```
 */


val httpClient = HttpClient {
    install(ContentNegotiation){
        json(
            Json{
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(Logging){
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        sanitizeHeader { header ->
            header == HttpHeaders.Authorization
        }
    }
}