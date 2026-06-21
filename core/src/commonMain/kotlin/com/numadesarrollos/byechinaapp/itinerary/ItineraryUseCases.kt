package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.domain.result.NDResult
import com.numadesarrollos.base.domain.usecase.NDParams
import com.numadesarrollos.base.domain.usecase.NDUseCase
import com.numadesarrollos.byechinaapp.domain.ChinaTime
import com.numadesarrollos.byechinaapp.domain.IdGenerator
import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState
import kotlinx.datetime.LocalDate

// ── Trip ─────────────────────────────────────────────────────────────────────

data class CreateTripParams(
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdBy: Owner,
) : NDParams

class CreateTripUseCase(
    private val tripRepository: TripRepository,
) : NDUseCase<CreateTripParams, Trip>() {
    override suspend fun doWork(params: CreateTripParams): NDResult<Trip> = NDResult.suspendRunCatching {
        val trip = Trip(
            id = IdGenerator.newId(),
            name = params.name,
            startDate = params.startDate,
            endDate = params.endDate,
            updatedAt = ChinaTime.nowEpochMillis(),
            deleted = false,
            syncState = SyncState.PENDING_UPLOAD,
            createdBy = params.createdBy,
        )
        tripRepository.insert(trip)
        trip
    }
}

class GetTripUseCase(
    private val tripRepository: TripRepository,
) : NDUseCase<NDParams.None, Trip?>() {
    override suspend fun doWork(params: NDParams.None): NDResult<Trip?> = NDResult.suspendRunCatching {
        tripRepository.getAll().firstOrNull()
    }
}

// ── Place ────────────────────────────────────────────────────────────────────

data class CreatePlaceParams(
    val tripId: String,
    val name: String,
    val createdBy: Owner,
) : NDParams

class CreatePlaceUseCase(
    private val placeRepository: PlaceRepository,
) : NDUseCase<CreatePlaceParams, Place>() {
    override suspend fun doWork(params: CreatePlaceParams): NDResult<Place> = NDResult.suspendRunCatching {
        val nextOrderIndex = placeRepository.getByTrip(params.tripId).size.toLong()
        val place = Place(
            id = IdGenerator.newId(),
            tripId = params.tripId,
            name = params.name,
            orderIndex = nextOrderIndex,
            updatedAt = ChinaTime.nowEpochMillis(),
            deleted = false,
            syncState = SyncState.PENDING_UPLOAD,
            createdBy = params.createdBy,
        )
        placeRepository.insert(place)
        place
    }
}

// ── Day ──────────────────────────────────────────────────────────────────────

data class CreateDayParams(
    val placeId: String,
    val date: LocalDate,
    val title: String?,
    val isSpecial: Boolean,
    val notes: String?,
    val createdBy: Owner,
) : NDParams

class CreateDayUseCase(
    private val dayRepository: DayRepository,
) : NDUseCase<CreateDayParams, Day>() {
    override suspend fun doWork(params: CreateDayParams): NDResult<Day> = NDResult.suspendRunCatching {
        val day = Day(
            id = IdGenerator.newId(),
            placeId = params.placeId,
            date = params.date,
            title = params.title,
            isSpecial = params.isSpecial,
            notes = params.notes,
            updatedAt = ChinaTime.nowEpochMillis(),
            deleted = false,
            syncState = SyncState.PENDING_UPLOAD,
            createdBy = params.createdBy,
        )
        dayRepository.insert(day)
        day
    }
}

class GetTodayUseCase(
    private val dayRepository: DayRepository,
) : NDUseCase<NDParams.None, Day?>() {
    override suspend fun doWork(params: NDParams.None): NDResult<Day?> = NDResult.suspendRunCatching {
        dayRepository.getByDate(ChinaTime.today())
    }
}

// ── Composite reads ────────────────────────────────────────────────────────────

data class GetItineraryParams(val tripId: String) : NDParams

class GetItineraryUseCase(
    private val placeRepository: PlaceRepository,
    private val dayRepository: DayRepository,
) : NDUseCase<GetItineraryParams, List<PlaceWithDays>>() {
    override suspend fun doWork(params: GetItineraryParams): NDResult<List<PlaceWithDays>> =
        NDResult.suspendRunCatching {
            placeRepository.getByTrip(params.tripId).map { place ->
                PlaceWithDays(place, dayRepository.getByPlace(place.id))
            }
        }
}

data class GetDayDetailParams(val dayId: String) : NDParams

class GetDayDetailUseCase(
    private val dayRepository: DayRepository,
    private val activityRepository: ActivityRepository,
    private val locationRepository: LocationRepository,
) : NDUseCase<GetDayDetailParams, DayDetail?>() {
    override suspend fun doWork(params: GetDayDetailParams): NDResult<DayDetail?> = NDResult.suspendRunCatching {
        val day = dayRepository.getById(params.dayId)
        if (day == null) {
            null
        } else {
            DayDetail(
                day = day,
                activities = activityRepository.getByDay(day.id),
                locations = locationRepository.getByDay(day.id),
            )
        }
    }
}
