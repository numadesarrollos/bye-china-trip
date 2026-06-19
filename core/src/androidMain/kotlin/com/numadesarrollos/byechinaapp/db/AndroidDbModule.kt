package com.numadesarrollos.byechinaapp.db

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val androidDbModule: Module = module {
    single { DatabaseDriverFactory(androidContext()) }
}
