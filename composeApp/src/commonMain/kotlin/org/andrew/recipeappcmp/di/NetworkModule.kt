package org.andrew.recipeappcmp.di

import io.ktor.client.HttpClient
import org.andrew.recipeappcmp.features.common.data.api.httpClient
import org.koin.dsl.module

fun networkModule() = module {

    single<HttpClient> {
        httpClient
    }
}