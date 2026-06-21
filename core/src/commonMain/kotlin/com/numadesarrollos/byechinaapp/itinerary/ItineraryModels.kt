package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Trip(
    val id: String,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class Place(
    val id: String,
    val tripId: String,
    val name: String,
    val orderIndex: Long,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class Day(
    val id: String,
    val placeId: String,
    val date: LocalDate,
    val title: String?,
    val isSpecial: Boolean,
    val notes: String?,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class Location(
    val id: String,
    val dayId: String,
    val name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val notes: String?,
    val orderIndex: Long,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class Activity(
    val id: String,
    val dayId: String,
    val title: String,
    val time: LocalTime?,
    val isCompleted: Boolean,
    val notes: String?,
    val orderIndex: Long,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

// ── Composite models for screens that need joined data ────────────────────────

data class PlaceWithDays(
    val place: Place,
    val days: List<Day>,
)

data class DayDetail(
    val day: Day,
    val activities: List<Activity>,
    val locations: List<Location>,
)
