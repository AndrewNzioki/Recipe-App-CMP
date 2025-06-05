package org.andrew.recipeappcmp

import org.andrew.recipeappcmp.di.initKoin
import org.koin.dsl.module

val iosModules = module{

}

fun initKoinIos() = initKoin(additionalModules = listOf(iosModules))
