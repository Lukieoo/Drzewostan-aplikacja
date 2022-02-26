package com.anioncode.drzewostan.core.app

import android.app.Application
import com.anioncode.drzewostan.core.di.koinInjector
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@App)
            modules(koinInjector)
        }
    }
}