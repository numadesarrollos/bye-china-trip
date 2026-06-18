package com.numadesarrollos.byechinaapp

import com.numadesarrollos.byechinaapp.di.allModules
import org.koin.core.context.startKoin

// Called from Swift before MainViewController() — see iOSApp.swift
fun initKoin() {
    startKoin {
        modules(allModules)
    }
}
