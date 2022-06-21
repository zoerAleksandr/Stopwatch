package com.gb.stopwatch

import android.app.Application
import com.gb.stopwatch.di.module
import com.gb.stopwatch.di.scope
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module, scope)
        }
    }
}