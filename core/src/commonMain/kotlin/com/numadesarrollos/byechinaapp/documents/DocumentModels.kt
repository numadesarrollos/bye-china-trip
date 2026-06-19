package com.numadesarrollos.byechinaapp.documents

import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState

data class Document(
    val id: String,
    val tripId: String,
    val name: String,
    val localPath: String,
    val remoteUrl: String?,
    val mimeType: String,
    val placeId: String?,
    val dayId: String?,
    val activityId: String?,
    val flightId: String?,
    val trainTripId: String?,
    val accommodationId: String?,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)
