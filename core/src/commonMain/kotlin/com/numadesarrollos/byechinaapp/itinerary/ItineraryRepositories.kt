package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.domain.repository.NDRepository
import com.numadesarrollos.byechinaapp.db.AppDatabase
import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import com.numadesarrollos.byechinaapp.db.Trip as TripEntity
import com.numadesarrollos.byechinaapp.db.Place as PlaceEntity
import com.numadesarrollos.byechinaapp.db.Day as DayEntity
import com.numadesarrollos.byechinaapp.db.Location as LocationEntity
import com.numadesarrollos.byechinaapp.db.Activity as ActivityEntity

// ── Trip ─────────────────────────────────────────────────────────────────────

interface TripRepository : NDRepository {
    suspend fun getAll(): List<Trip>
    suspend fun getById(id: String): Trip?
    suspend fun insert(trip: Trip)
    suspend fun update(trip: Trip)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class TripRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : TripRepository {

    override suspend fun getAll(): List<Trip> = withContext(dispatcherProvider.io) {
        db.tripQueries.selectAll().executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): Trip? = withContext(dispatcherProvider.io) {
        db.tripQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(trip: Trip) = withContext(dispatcherProvider.io) {
        db.tripQueries.insert(
            id = trip.id,
            name = trip.name,
            startDate = trip.startDate.toString(),
            endDate = trip.endDate.toString(),
            updatedAt = trip.updatedAt,
            deleted = trip.deleted.toLong(),
            syncState = trip.syncState.toDb(),
            createdBy = trip.createdBy.toDb(),
        )
    }

    override suspend fun update(trip: Trip) = withContext(dispatcherProvider.io) {
        db.tripQueries.update(
            name = trip.name,
            startDate = trip.startDate.toString(),
            endDate = trip.endDate.toString(),
            updatedAt = trip.updatedAt,
            syncState = trip.syncState.toDb(),
            id = trip.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.tripQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun TripEntity.toDomain() = Trip(
        id = id,
        name = name,
        startDate = LocalDate.parse(startDate),
        endDate = LocalDate.parse(endDate),
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── Place ────────────────────────────────────────────────────────────────────

interface PlaceRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<Place>
    suspend fun getById(id: String): Place?
    suspend fun insert(place: Place)
    suspend fun update(place: Place)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class PlaceRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : PlaceRepository {

    override suspend fun getByTrip(tripId: String): List<Place> = withContext(dispatcherProvider.io) {
        db.placeQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): Place? = withContext(dispatcherProvider.io) {
        db.placeQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(place: Place) = withContext(dispatcherProvider.io) {
        db.placeQueries.insert(
            id = place.id,
            tripId = place.tripId,
            name = place.name,
            orderIndex = place.orderIndex,
            updatedAt = place.updatedAt,
            deleted = place.deleted.toLong(),
            syncState = place.syncState.toDb(),
            createdBy = place.createdBy.toDb(),
        )
    }

    override suspend fun update(place: Place) = withContext(dispatcherProvider.io) {
        db.placeQueries.update(
            name = place.name,
            orderIndex = place.orderIndex,
            updatedAt = place.updatedAt,
            syncState = place.syncState.toDb(),
            id = place.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.placeQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun PlaceEntity.toDomain() = Place(
        id = id,
        tripId = tripId,
        name = name,
        orderIndex = orderIndex,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── Day ──────────────────────────────────────────────────────────────────────

interface DayRepository : NDRepository {
    suspend fun getByPlace(placeId: String): List<Day>
    suspend fun getByDate(date: LocalDate): Day?
    suspend fun getById(id: String): Day?
    suspend fun insert(day: Day)
    suspend fun update(day: Day)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class DayRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : DayRepository {

    override suspend fun getByPlace(placeId: String): List<Day> = withContext(dispatcherProvider.io) {
        db.dayQueries.selectByPlace(placeId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByDate(date: LocalDate): Day? = withContext(dispatcherProvider.io) {
        db.dayQueries.selectByDate(date.toString()).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun getById(id: String): Day? = withContext(dispatcherProvider.io) {
        db.dayQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(day: Day) = withContext(dispatcherProvider.io) {
        db.dayQueries.insert(
            id = day.id,
            placeId = day.placeId,
            date = day.date.toString(),
            title = day.title,
            isSpecial = day.isSpecial.toLong(),
            notes = day.notes,
            updatedAt = day.updatedAt,
            deleted = day.deleted.toLong(),
            syncState = day.syncState.toDb(),
            createdBy = day.createdBy.toDb(),
        )
    }

    override suspend fun update(day: Day) = withContext(dispatcherProvider.io) {
        db.dayQueries.update(
            date = day.date.toString(),
            title = day.title,
            isSpecial = day.isSpecial.toLong(),
            notes = day.notes,
            updatedAt = day.updatedAt,
            syncState = day.syncState.toDb(),
            id = day.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.dayQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun DayEntity.toDomain() = Day(
        id = id,
        placeId = placeId,
        date = LocalDate.parse(date),
        title = title,
        isSpecial = isSpecial.toBoolean(),
        notes = notes,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── Location ─────────────────────────────────────────────────────────────────

interface LocationRepository : NDRepository {
    suspend fun getByDay(dayId: String): List<Location>
    suspend fun getById(id: String): Location?
    suspend fun insert(location: Location)
    suspend fun update(location: Location)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class LocationRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : LocationRepository {

    override suspend fun getByDay(dayId: String): List<Location> = withContext(dispatcherProvider.io) {
        db.locationQueries.selectByDay(dayId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): Location? = withContext(dispatcherProvider.io) {
        db.locationQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(location: Location) = withContext(dispatcherProvider.io) {
        db.locationQueries.insert(
            id = location.id,
            dayId = location.dayId,
            name = location.name,
            address = location.address,
            latitude = location.latitude,
            longitude = location.longitude,
            notes = location.notes,
            orderIndex = location.orderIndex,
            updatedAt = location.updatedAt,
            deleted = location.deleted.toLong(),
            syncState = location.syncState.toDb(),
            createdBy = location.createdBy.toDb(),
        )
    }

    override suspend fun update(location: Location) = withContext(dispatcherProvider.io) {
        db.locationQueries.update(
            name = location.name,
            address = location.address,
            latitude = location.latitude,
            longitude = location.longitude,
            notes = location.notes,
            orderIndex = location.orderIndex,
            updatedAt = location.updatedAt,
            syncState = location.syncState.toDb(),
            id = location.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.locationQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun LocationEntity.toDomain() = Location(
        id = id,
        dayId = dayId,
        name = name,
        address = address,
        latitude = latitude,
        longitude = longitude,
        notes = notes,
        orderIndex = orderIndex,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── Activity ─────────────────────────────────────────────────────────────────

interface ActivityRepository : NDRepository {
    suspend fun getByDay(dayId: String): List<Activity>
    suspend fun getById(id: String): Activity?
    suspend fun insert(activity: Activity)
    suspend fun update(activity: Activity)
    suspend fun setCompleted(id: String, isCompleted: Boolean, updatedAt: Long)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class ActivityRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : ActivityRepository {

    override suspend fun getByDay(dayId: String): List<Activity> = withContext(dispatcherProvider.io) {
        db.activityQueries.selectByDay(dayId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): Activity? = withContext(dispatcherProvider.io) {
        db.activityQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(activity: Activity) = withContext(dispatcherProvider.io) {
        db.activityQueries.insert(
            id = activity.id,
            dayId = activity.dayId,
            title = activity.title,
            time = activity.time?.toString(),
            isCompleted = activity.isCompleted.toLong(),
            notes = activity.notes,
            orderIndex = activity.orderIndex,
            updatedAt = activity.updatedAt,
            deleted = activity.deleted.toLong(),
            syncState = activity.syncState.toDb(),
            createdBy = activity.createdBy.toDb(),
        )
    }

    override suspend fun update(activity: Activity) = withContext(dispatcherProvider.io) {
        db.activityQueries.update(
            title = activity.title,
            time = activity.time?.toString(),
            notes = activity.notes,
            orderIndex = activity.orderIndex,
            updatedAt = activity.updatedAt,
            syncState = activity.syncState.toDb(),
            id = activity.id,
        )
    }

    override suspend fun setCompleted(id: String, isCompleted: Boolean, updatedAt: Long) =
        withContext(dispatcherProvider.io) {
            db.activityQueries.setCompleted(isCompleted = isCompleted.toLong(), updatedAt = updatedAt, id = id)
        }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.activityQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun ActivityEntity.toDomain() = Activity(
        id = id,
        dayId = dayId,
        title = title,
        time = time?.let { LocalTime.parse(it) },
        isCompleted = isCompleted.toBoolean(),
        notes = notes,
        orderIndex = orderIndex,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

private fun Boolean.toLong(): Long = if (this) 1L else 0L
private fun Long.toBoolean(): Boolean = this != 0L
