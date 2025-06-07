package org.andrew.recipeappcmp.di

import org.andrew.recipeappcmp.dbFactory.DatabaseFactory
import org.koin.dsl.module

val jsModules = module{
    single { DatabaseFactory() }
}

fun initKoinJs() = initKoin(additionalModules = listOf(jsModules))