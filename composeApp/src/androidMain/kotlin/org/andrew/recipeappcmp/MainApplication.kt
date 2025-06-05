package org.andrew.recipeappcmp

import android.app.Application
import org.andrew.recipeappcmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class MainApplication: Application() {

    private val androidModules = module{

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