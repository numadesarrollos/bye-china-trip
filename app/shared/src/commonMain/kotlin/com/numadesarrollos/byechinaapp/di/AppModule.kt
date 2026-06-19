package com.numadesarrollos.byechinaapp.di

import com.numadesarrollos.base.domain.di.domainModule
import com.numadesarrollos.byechinaapp.auth.LoginViewModel
import com.numadesarrollos.byechinaapp.db.dbModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule: Module = module {
    viewModelOf(::LoginViewModel)
}

// Platform-agnostic modules. Each platform entry point additionally provides
// its own DatabaseDriverFactory module (androidDbModule / iosDbModule) before this list.
val allModules: List<Module> = listOf(
    domainModule,
    coreModule,
    dbModule,
    appModule,
)
