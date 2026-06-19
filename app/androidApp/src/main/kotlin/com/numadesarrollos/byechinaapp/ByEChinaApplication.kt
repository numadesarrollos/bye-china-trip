package com.numadesarrollos.byechinaapp

import android.app.Application
import com.numadesarrollos.byechinaapp.db.androidDbModule
import com.numadesarrollos.byechinaapp.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ByEChinaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ByEChinaApplication)
            modules(listOf(androidDbModule) + allModules)
        }
    }
}
