package org.andrew.recipeappcmp

import android.app.Application
import org.andrew.recipeappcmp.dbFactory.DatabaseFactory
import org.andrew.recipeappcmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class MainApplication: Application() {

    private val androidModules = module{
        single{ DatabaseFactory(applicationContext) }
    }
    override fun onCreate(){
        super.onCreate()
    }

    fun setupKoin(){
        initKoin(
            additionalModules = listOf(androidModules)
        ){
            androidContext(applicationContext)
        }
    }
}