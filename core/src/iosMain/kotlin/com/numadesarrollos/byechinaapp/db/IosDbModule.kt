package com.numadesarrollos.byechinaapp.db

import org.koin.core.module.Module
import org.koin.dsl.module

val iosDbModule: Module = module {
    single { DatabaseDriverFactory() }
}
