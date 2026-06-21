package com.numadesarrollos.byechinaapp.di

import com.numadesarrollos.byechinaapp.auth.AuthRepository
import com.numadesarrollos.byechinaapp.auth.AuthRepositoryImpl
import com.numadesarrollos.byechinaapp.auth.LoginUseCase
import com.numadesarrollos.byechinaapp.itinerary.CreateDayUseCase
import com.numadesarrollos.byechinaapp.itinerary.CreatePlaceUseCase
import com.numadesarrollos.byechinaapp.itinerary.CreateTripUseCase
import com.numadesarrollos.byechinaapp.itinerary.GetDayDetailUseCase
import com.numadesarrollos.byechinaapp.itinerary.GetItineraryUseCase
import com.numadesarrollos.byechinaapp.itinerary.GetTodayUseCase
import com.numadesarrollos.byechinaapp.itinerary.GetTripUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule: Module = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    factory { LoginUseCase(get()) }

    factory { CreateTripUseCase(get()) }
    factory { GetTripUseCase(get()) }
    factory { CreatePlaceUseCase(get()) }
    factory { CreateDayUseCase(get()) }
    factory { GetTodayUseCase(get()) }
    factory { GetItineraryUseCase(get(), get()) }
    factory { GetDayDetailUseCase(get(), get(), get()) }
}
