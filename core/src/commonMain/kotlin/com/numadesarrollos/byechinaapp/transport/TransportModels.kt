package com.numadesarrollos.byechinaapp.transport

import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState
import kotlinx.datetime.LocalDateTime

data class Flight(
    val id: String,
    val tripId: String,
    val airline: String,
    val flightNumber: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureDateTime: LocalDateTime,
    val arrivalDateTime: LocalDateTime,
    val seat: String?,
    val priceAmount: Double?,
    val priceCurrency: String?,
    val notes: String?,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)

data class TrainTrip(
    val id: String,
    val tripId: String,
    val trainNumber: String,
    val departureStation: String,
    val arrivalStation: String,
    val departureDateTime: LocalDateTime,
    val arrivalDateTime: LocalDateTime,
    val seat: String?,
    val priceAmount: Double?,
    val priceCurrency: String?,
    val notes: String?,
    val updatedAt: Long,
    val deleted: Boolean,
    val syncState: SyncState,
    val createdBy: Owner,
)
