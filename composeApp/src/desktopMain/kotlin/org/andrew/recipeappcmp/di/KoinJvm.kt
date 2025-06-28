package org.andrew.recipeappcmp.di

import org.andrew.recipeappcmp.dbFactory.DatabaseFactory
import org.andrew.recipeappcmp.preferences.MultiplatformSettingsFactory
import org.koin.dsl.module

val jvmModules = module{
    single { DatabaseFactory() }
    single{ MultiplatformSettingsFactory() }
}

fun initKoinJvm() = initKoin(additionalModules = listOf(jvmModules))