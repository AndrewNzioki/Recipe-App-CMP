package org.andrew.recipeappcmp.di

import org.andrew.recipeappcmp.dbFactory.DatabaseFactory
import org.koin.dsl.module

val iosModules = module{
    single { DatabaseFactory() }
}

fun initKoinIos() = initKoin(additionalModules = listOf(iosModules))
