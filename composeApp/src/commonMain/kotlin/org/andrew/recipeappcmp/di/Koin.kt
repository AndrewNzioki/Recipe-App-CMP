package org.andrew.recipeappcmp.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
/**
 * Initializes the Koin dependency injection framework for the application.
 *
 * This function sets up Koin by applying the provided application-level declarations and
 * loading any additional Koin modules passed to it. It wraps `startKoin` and provides
 * a convenient entry point for setting up Koin in a testable and modular way.
 *
 * @param additionalModules A list of Koin modules to be loaded into the application context.
 *                          This can include feature-specific or environment-specific modules.
 * @param appDeclaration A lambda where additional Koin configuration can be declared, such as
 *                       setting the application context or properties.
 *
 * Example usage:
 * ```
 * initKoin(
 *     additionalModules = listOf(networkModule, databaseModule)
 * ) {
 *     androidContext(this@MyApplication)
 * }
 * ```
 */


fun initKoin(
    additionalModules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {}
) = startKoin{
    appDeclaration()
    modules(additionalModules + cacheModule() + networkModule() + dataModule() + viewModelModule())
}