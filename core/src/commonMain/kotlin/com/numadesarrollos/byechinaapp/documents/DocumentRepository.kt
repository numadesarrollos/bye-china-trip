package com.numadesarrollos.byechinaapp.documents

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.domain.repository.NDRepository
import com.numadesarrollos.byechinaapp.db.AppDatabase
import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState
import kotlinx.coroutines.withContext
import com.numadesarrollos.byechinaapp.db.Document as DocumentEntity

interface DocumentRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<Document>
    suspend fun getByDay(dayId: String): List<Document>
    suspend fun getByActivity(activityId: String): List<Document>
    suspend fun getByFlight(flightId: String): List<Document>
    suspend fun getByTrainTrip(trainTripId: String): List<Document>
    suspend fun getByAccommodation(accommodationId: String): List<Document>
    suspend fun getById(id: String): Document?
    suspend fun insert(document: Document)
    suspend fun update(document: Document)
    suspend fun markSynced(id: String, remoteUrl: String)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class DocumentRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : DocumentRepository {

    override suspend fun getByTrip(tripId: String): List<Document> = withContext(dispatcherProvider.io) {
        db.documentQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByDay(dayId: String): List<Document> = withContext(dispatcherProvider.io) {
        db.documentQueries.selectByDay(dayId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByActivity(activityId: String): List<Document> = withContext(dispatcherProvider.io) {
        db.documentQueries.selectByActivity(activityId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByFlight(flightId: String): List<Document> = withContext(dispatcherProvider.io) {
        db.documentQueries.selectByFlight(flightId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByTrainTrip(trainTripId: String): List<Document> = withContext(dispatcherProvider.io) {
        db.documentQueries.selectByTrainTrip(trainTripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByAccommodation(accommodationId: String): List<Document> = withContext(dispatcherProvider.io) {
        db.documentQueries.selectByAccommodation(accommodationId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): Document? = withContext(dispatcherProvider.io) {
        db.documentQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(document: Document) = withContext(dispatcherProvider.io) {
        db.documentQueries.insert(
            id = document.id,
            tripId = document.tripId,
            name = document.name,
            localPath = document.localPath,
            remoteUrl = document.remoteUrl,
            mimeType = document.mimeType,
            placeId = document.placeId,
            dayId = document.dayId,
            activityId = document.activityId,
            flightId = document.flightId,
            trainTripId = document.trainTripId,
            accommodationId = document.accommodationId,
            updatedAt = document.updatedAt,
            deleted = document.deleted.toLong(),
            syncState = document.syncState.toDb(),
            createdBy = document.createdBy.toDb(),
        )
    }

    override suspend fun update(document: Document) = withContext(dispatcherProvider.io) {
        db.documentQueries.update(
            name = document.name,
            localPath = document.localPath,
            remoteUrl = document.remoteUrl,
            mimeType = document.mimeType,
            updatedAt = document.updatedAt,
            syncState = document.syncState.toDb(),
            id = document.id,
        )
    }

    override suspend fun markSynced(id: String, remoteUrl: String) = withContext(dispatcherProvider.io) {
        db.documentQueries.markSynced(remoteUrl = remoteUrl, id = id)
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.documentQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun DocumentEntity.toDomain() = Document(
        id = id,
        tripId = tripId,
        name = name,
        localPath = localPath,
        remoteUrl = remoteUrl,
        mimeType = mimeType,
        placeId = placeId,
        dayId = dayId,
        activityId = activityId,
        flightId = flightId,
        trainTripId = trainTripId,
        accommodationId = accommodationId,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

private fun Boolean.toLong(): Long = if (this) 1L else 0L
private fun Long.toBoolean(): Boolean = this != 0L
