package com.numadesarrollos.byechinaapp.di

import com.numadesarrollos.base.domain.di.domainModule
import com.numadesarrollos.byechinaapp.auth.LoginViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule: Module = module {
    viewModelOf(::LoginViewModel)
}

// All modules that must be started together
val allModules: List<Module> = listOf(
    domainModule,
    coreModule,
    appModule,
)
