package org.andrew.recipeappcmp.di

import org.andrew.recipeappcmp.dbFactory.DatabaseFactory
import org.koin.dsl.module

val jvmModules = module{
    single { DatabaseFactory() }
}

fun initKoinJvm() = initKoin(additionalModules = listOf(jvmModules))