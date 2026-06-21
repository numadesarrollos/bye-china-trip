package com.numadesarrollos.byechinaapp.di

import com.numadesarrollos.base.domain.di.domainModule
import com.numadesarrollos.byechinaapp.auth.LoginViewModel
import com.numadesarrollos.byechinaapp.db.dbModule
import com.numadesarrollos.byechinaapp.itinerary.DayDetailViewModel
import com.numadesarrollos.byechinaapp.itinerary.DayFormViewModel
import com.numadesarrollos.byechinaapp.itinerary.ItineraryViewModel
import com.numadesarrollos.byechinaapp.itinerary.PlaceFormViewModel
import com.numadesarrollos.byechinaapp.itinerary.TripFormViewModel
import com.numadesarrollos.byechinaapp.today.TodayViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule: Module = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::TodayViewModel)
    viewModelOf(::ItineraryViewModel)
    viewModelOf(::TripFormViewModel)

    viewModel { params -> PlaceFormViewModel(params.get(), get(), get()) }
    viewModel { params -> DayFormViewModel(params.get(), get(), get()) }
    viewModel { params -> DayDetailViewModel(params.get(), get(), get()) }
}

// Platform-agnostic modules. Each platform entry point additionally provides
// its own DatabaseDriverFactory module (androidDbModule / iosDbModule) before this list.
val allModules: List<Module> = listOf(
    domainModule,
    coreModule,
    dbModule,
    appModule,
)
