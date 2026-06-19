package com.numadesarrollos.byechinaapp.db

import com.numadesarrollos.byechinaapp.documents.DocumentRepository
import com.numadesarrollos.byechinaapp.documents.DocumentRepositoryImpl
import com.numadesarrollos.byechinaapp.extras.AccommodationRepository
import com.numadesarrollos.byechinaapp.extras.AccommodationRepositoryImpl
import com.numadesarrollos.byechinaapp.extras.ChecklistItemRepository
import com.numadesarrollos.byechinaapp.extras.ChecklistItemRepositoryImpl
import com.numadesarrollos.byechinaapp.extras.CurrencyRateRepository
import com.numadesarrollos.byechinaapp.extras.CurrencyRateRepositoryImpl
import com.numadesarrollos.byechinaapp.extras.DiaryEntryRepository
import com.numadesarrollos.byechinaapp.extras.DiaryEntryRepositoryImpl
import com.numadesarrollos.byechinaapp.extras.ExpenseRepository
import com.numadesarrollos.byechinaapp.extras.ExpenseRepositoryImpl
import com.numadesarrollos.byechinaapp.extras.PhraseRepository
import com.numadesarrollos.byechinaapp.extras.PhraseRepositoryImpl
import com.numadesarrollos.byechinaapp.extras.ReminderRepository
import com.numadesarrollos.byechinaapp.extras.ReminderRepositoryImpl
import com.numadesarrollos.byechinaapp.itinerary.ActivityRepository
import com.numadesarrollos.byechinaapp.itinerary.ActivityRepositoryImpl
import com.numadesarrollos.byechinaapp.itinerary.DayRepository
import com.numadesarrollos.byechinaapp.itinerary.DayRepositoryImpl
import com.numadesarrollos.byechinaapp.itinerary.LocationRepository
import com.numadesarrollos.byechinaapp.itinerary.LocationRepositoryImpl
import com.numadesarrollos.byechinaapp.itinerary.PlaceRepository
import com.numadesarrollos.byechinaapp.itinerary.PlaceRepositoryImpl
import com.numadesarrollos.byechinaapp.itinerary.TripRepository
import com.numadesarrollos.byechinaapp.itinerary.TripRepositoryImpl
import com.numadesarrollos.byechinaapp.transport.FlightRepository
import com.numadesarrollos.byechinaapp.transport.FlightRepositoryImpl
import com.numadesarrollos.byechinaapp.transport.TrainTripRepository
import com.numadesarrollos.byechinaapp.transport.TrainTripRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

// Assumes a platform module already provided `DatabaseDriverFactory` (see androidDbModule / iosDbModule)
val dbModule: Module = module {
    single { AppDatabase(driver = get<DatabaseDriverFactory>().createDriver()) }

    single<TripRepository> { TripRepositoryImpl(get(), get()) }
    single<PlaceRepository> { PlaceRepositoryImpl(get(), get()) }
    single<DayRepository> { DayRepositoryImpl(get(), get()) }
    single<LocationRepository> { LocationRepositoryImpl(get(), get()) }
    single<ActivityRepository> { ActivityRepositoryImpl(get(), get()) }

    single<DocumentRepository> { DocumentRepositoryImpl(get(), get()) }

    single<FlightRepository> { FlightRepositoryImpl(get(), get()) }
    single<TrainTripRepository> { TrainTripRepositoryImpl(get(), get()) }

    single<ExpenseRepository> { ExpenseRepositoryImpl(get(), get()) }
    single<CurrencyRateRepository> { CurrencyRateRepositoryImpl(get(), get()) }
    single<ChecklistItemRepository> { ChecklistItemRepositoryImpl(get(), get()) }
    single<AccommodationRepository> { AccommodationRepositoryImpl(get(), get()) }
    single<DiaryEntryRepository> { DiaryEntryRepositoryImpl(get(), get()) }
    single<ReminderRepository> { ReminderRepositoryImpl(get(), get()) }
    single<PhraseRepository> { PhraseRepositoryImpl(get(), get()) }
}
