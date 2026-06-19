package com.numadesarrollos.byechinaapp.extras

import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Expense(
    val id: String,
    val tripId: String,
    val category: String, // Transporte | Hoteles | Comida | Entradas | Compras | Otros
    val description: String,
    val amount: Double,
    val currency: String, // EUR | CNY
    val date: LocalDate,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class CurrencyRate(
    val id: String,
    val tripId: String,
    val eurToCny: Double,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class ChecklistItem(
    val id: String,
    val tripId: String,
    val title: String,
    val isCompleted: Boolean,
    val category: String?,
    val orderIndex: Long,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class Accommodation(
    val id: String,
    val tripId: String,
    val placeId: String?,
    val name: String,
    val address: String?,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val confirmationCode: String?,
    val notes: String?,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class DiaryEntry(
    val id: String,
    val tripId: String,
    val dayId: String?,
    val text: String?,
    val photoLocalPaths: List<String>,
    val date: LocalDate,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class Reminder(
    val id: String,
    val tripId: String,
    val title: String,
    val dateTime: LocalDateTime,
    val isTriggered: Boolean,
    val flightId: String?,
    val trainTripId: String?,
    val activityId: String?,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class Phrase(
    val id: String,
    val spanish: String,
    val chinese: String,
    val pinyin: String,
    val category: String,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)
