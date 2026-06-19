package com.numadesarrollos.byechinaapp.transport

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.domain.repository.NDRepository
import com.numadesarrollos.byechinaapp.db.AppDatabase
import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import com.numadesarrollos.byechinaapp.db.Flight as FlightEntity
import com.numadesarrollos.byechinaapp.db.TrainTrip as TrainTripEntity

// ── Flight ───────────────────────────────────────────────────────────────────

interface FlightRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<Flight>
    suspend fun getById(id: String): Flight?
    suspend fun insert(flight: Flight)
    suspend fun update(flight: Flight)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class FlightRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : FlightRepository {

    override suspend fun getByTrip(tripId: String): List<Flight> = withContext(dispatcherProvider.io) {
        db.flightQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): Flight? = withContext(dispatcherProvider.io) {
        db.flightQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(flight: Flight) = withContext(dispatcherProvider.io) {
        db.flightQueries.insert(
            id = flight.id,
            tripId = flight.tripId,
            airline = flight.airline,
            flightNumber = flight.flightNumber,
            departureAirport = flight.departureAirport,
            arrivalAirport = flight.arrivalAirport,
            departureDateTime = flight.departureDateTime.toString(),
            arrivalDateTime = flight.arrivalDateTime.toString(),
            seat = flight.seat,
            priceAmount = flight.priceAmount,
            priceCurrency = flight.priceCurrency,
            notes = flight.notes,
            updatedAt = flight.updatedAt,
            deleted = flight.deleted.toLong(),
            syncState = flight.syncState.toDb(),
            createdBy = flight.createdBy.toDb(),
        )
    }

    override suspend fun update(flight: Flight) = withContext(dispatcherProvider.io) {
        db.flightQueries.update(
            airline = flight.airline,
            flightNumber = flight.flightNumber,
            departureAirport = flight.departureAirport,
            arrivalAirport = flight.arrivalAirport,
            departureDateTime = flight.departureDateTime.toString(),
            arrivalDateTime = flight.arrivalDateTime.toString(),
            seat = flight.seat,
            priceAmount = flight.priceAmount,
            priceCurrency = flight.priceCurrency,
            notes = flight.notes,
            updatedAt = flight.updatedAt,
            syncState = flight.syncState.toDb(),
            id = flight.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.flightQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun FlightEntity.toDomain() = Flight(
        id = id,
        tripId = tripId,
        airline = airline,
        flightNumber = flightNumber,
        departureAirport = departureAirport,
        arrivalAirport = arrivalAirport,
        departureDateTime = LocalDateTime.parse(departureDateTime),
        arrivalDateTime = LocalDateTime.parse(arrivalDateTime),
        seat = seat,
        priceAmount = priceAmount,
        priceCurrency = priceCurrency,
        notes = notes,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── TrainTrip ────────────────────────────────────────────────────────────────

interface TrainTripRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<TrainTrip>
    suspend fun getById(id: String): TrainTrip?
    suspend fun insert(trainTrip: TrainTrip)
    suspend fun update(trainTrip: TrainTrip)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class TrainTripRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : TrainTripRepository {

    override suspend fun getByTrip(tripId: String): List<TrainTrip> = withContext(dispatcherProvider.io) {
        db.trainTripQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): TrainTrip? = withContext(dispatcherProvider.io) {
        db.trainTripQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(trainTrip: TrainTrip) = withContext(dispatcherProvider.io) {
        db.trainTripQueries.insert(
            id = trainTrip.id,
            tripId = trainTrip.tripId,
            trainNumber = trainTrip.trainNumber,
            departureStation = trainTrip.departureStation,
            arrivalStation = trainTrip.arrivalStation,
            departureDateTime = trainTrip.departureDateTime.toString(),
            arrivalDateTime = trainTrip.arrivalDateTime.toString(),
            seat = trainTrip.seat,
            priceAmount = trainTrip.priceAmount,
            priceCurrency = trainTrip.priceCurrency,
            notes = trainTrip.notes,
            updatedAt = trainTrip.updatedAt,
            deleted = trainTrip.deleted.toLong(),
            syncState = trainTrip.syncState.toDb(),
            createdBy = trainTrip.createdBy.toDb(),
        )
    }

    override suspend fun update(trainTrip: TrainTrip) = withContext(dispatcherProvider.io) {
        db.trainTripQueries.update(
            trainNumber = trainTrip.trainNumber,
            departureStation = trainTrip.departureStation,
            arrivalStation = trainTrip.arrivalStation,
            departureDateTime = trainTrip.departureDateTime.toString(),
            arrivalDateTime = trainTrip.arrivalDateTime.toString(),
            seat = trainTrip.seat,
            priceAmount = trainTrip.priceAmount,
            priceCurrency = trainTrip.priceCurrency,
            notes = trainTrip.notes,
            updatedAt = trainTrip.updatedAt,
            syncState = trainTrip.syncState.toDb(),
            id = trainTrip.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.trainTripQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun TrainTripEntity.toDomain() = TrainTrip(
        id = id,
        tripId = tripId,
        trainNumber = trainNumber,
        departureStation = departureStation,
        arrivalStation = arrivalStation,
        departureDateTime = LocalDateTime.parse(departureDateTime),
        arrivalDateTime = LocalDateTime.parse(arrivalDateTime),
        seat = seat,
        priceAmount = priceAmount,
        priceCurrency = priceCurrency,
        notes = notes,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

private fun Boolean.toLong(): Long = if (this) 1L else 0L
private fun Long.toBoolean(): Boolean = this != 0L
