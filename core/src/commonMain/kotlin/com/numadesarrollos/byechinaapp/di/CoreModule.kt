package com.numadesarrollos.byechinaapp.di

import com.numadesarrollos.byechinaapp.auth.AuthRepository
import com.numadesarrollos.byechinaapp.auth.AuthRepositoryImpl
import com.numadesarrollos.byechinaapp.auth.LoginUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule: Module = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    factory { LoginUseCase(get()) }
}
