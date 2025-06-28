package org.andrew.recipeappcmp.di

import org.andrew.recipeappcmp.dbFactory.DatabaseFactory
import org.andrew.recipeappcmp.preferences.MultiplatformSettingsFactory
import org.koin.dsl.module

val iosModules = module{
    single { DatabaseFactory() }
    single{ MultiplatformSettingsFactory() }
}

fun initKoinIos() = initKoin(additionalModules = listOf(iosModules))
