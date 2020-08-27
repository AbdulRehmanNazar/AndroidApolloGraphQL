package com.app.countries

import android.app.Application
import com.example.rickandmortyapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class GraphQLApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }


    private fun initKoin() {
        val modules = listOf(viewModelModules, dataSourceModules, repositoryModules, networkModules)
        startKoin {
            androidContext(this@GraphQLApplication)
            modules(modules)
        }

    }
}