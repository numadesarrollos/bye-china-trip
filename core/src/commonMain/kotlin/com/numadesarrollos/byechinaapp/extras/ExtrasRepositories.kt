package com.numadesarrollos.byechinaapp.extras

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.domain.repository.NDRepository
import com.numadesarrollos.byechinaapp.db.AppDatabase
import com.numadesarrollos.byechinaapp.domain.Owner
import com.numadesarrollos.byechinaapp.domain.SyncState
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import com.numadesarrollos.byechinaapp.db.Expense as ExpenseEntity
import com.numadesarrollos.byechinaapp.db.CurrencyRate as CurrencyRateEntity
import com.numadesarrollos.byechinaapp.db.ChecklistItem as ChecklistItemEntity
import com.numadesarrollos.byechinaapp.db.Accommodation as AccommodationEntity
import com.numadesarrollos.byechinaapp.db.DiaryEntry as DiaryEntryEntity
import com.numadesarrollos.byechinaapp.db.Reminder as ReminderEntity
import com.numadesarrollos.byechinaapp.db.Phrase as PhraseEntity

// ── Expense ──────────────────────────────────────────────────────────────────

interface ExpenseRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<Expense>
    suspend fun getByTripAndCategory(tripId: String, category: String): List<Expense>
    suspend fun getById(id: String): Expense?
    suspend fun insert(expense: Expense)
    suspend fun update(expense: Expense)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class ExpenseRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : ExpenseRepository {

    override suspend fun getByTrip(tripId: String): List<Expense> = withContext(dispatcherProvider.io) {
        db.expenseQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByTripAndCategory(tripId: String, category: String): List<Expense> =
        withContext(dispatcherProvider.io) {
            db.expenseQueries.selectByTripAndCategory(tripId, category).executeAsList().map { it.toDomain() }
        }

    override suspend fun getById(id: String): Expense? = withContext(dispatcherProvider.io) {
        db.expenseQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(expense: Expense) = withContext(dispatcherProvider.io) {
        db.expenseQueries.insert(
            id = expense.id,
            tripId = expense.tripId,
            category = expense.category,
            description = expense.description,
            amount = expense.amount,
            currency = expense.currency,
            date = expense.date.toString(),
            updatedAt = expense.updatedAt,
            deleted = expense.deleted.toLong(),
            syncState = expense.syncState.toDb(),
            createdBy = expense.createdBy.toDb(),
        )
    }

    override suspend fun update(expense: Expense) = withContext(dispatcherProvider.io) {
        db.expenseQueries.update(
            category = expense.category,
            description = expense.description,
            amount = expense.amount,
            currency = expense.currency,
            date = expense.date.toString(),
            updatedAt = expense.updatedAt,
            syncState = expense.syncState.toDb(),
            id = expense.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.expenseQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun ExpenseEntity.toDomain() = Expense(
        id = id,
        tripId = tripId,
        category = category,
        description = description,
        amount = amount,
        currency = currency,
        date = LocalDate.parse(date),
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── CurrencyRate ─────────────────────────────────────────────────────────────

interface CurrencyRateRepository : NDRepository {
    suspend fun getByTrip(tripId: String): CurrencyRate?
    suspend fun insert(rate: CurrencyRate)
    suspend fun update(rate: CurrencyRate)
}

class CurrencyRateRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : CurrencyRateRepository {

    override suspend fun getByTrip(tripId: String): CurrencyRate? = withContext(dispatcherProvider.io) {
        db.currencyRateQueries.selectByTrip(tripId).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(rate: CurrencyRate) = withContext(dispatcherProvider.io) {
        db.currencyRateQueries.insert(
            id = rate.id,
            tripId = rate.tripId,
            eurToCny = rate.eurToCny,
            updatedAt = rate.updatedAt,
            deleted = rate.deleted.toLong(),
            syncState = rate.syncState.toDb(),
            createdBy = rate.createdBy.toDb(),
        )
    }

    override suspend fun update(rate: CurrencyRate) = withContext(dispatcherProvider.io) {
        db.currencyRateQueries.update(
            eurToCny = rate.eurToCny,
            updatedAt = rate.updatedAt,
            syncState = rate.syncState.toDb(),
            id = rate.id,
        )
    }

    private fun CurrencyRateEntity.toDomain() = CurrencyRate(
        id = id,
        tripId = tripId,
        eurToCny = eurToCny,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── ChecklistItem ────────────────────────────────────────────────────────────

interface ChecklistItemRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<ChecklistItem>
    suspend fun getById(id: String): ChecklistItem?
    suspend fun insert(item: ChecklistItem)
    suspend fun update(item: ChecklistItem)
    suspend fun setCompleted(id: String, isCompleted: Boolean, updatedAt: Long)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class ChecklistItemRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : ChecklistItemRepository {

    override suspend fun getByTrip(tripId: String): List<ChecklistItem> = withContext(dispatcherProvider.io) {
        db.checklistItemQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): ChecklistItem? = withContext(dispatcherProvider.io) {
        db.checklistItemQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(item: ChecklistItem) = withContext(dispatcherProvider.io) {
        db.checklistItemQueries.insert(
            id = item.id,
            tripId = item.tripId,
            title = item.title,
            isCompleted = item.isCompleted.toLong(),
            category = item.category,
            orderIndex = item.orderIndex,
            updatedAt = item.updatedAt,
            deleted = item.deleted.toLong(),
            syncState = item.syncState.toDb(),
            createdBy = item.createdBy.toDb(),
        )
    }

    override suspend fun update(item: ChecklistItem) = withContext(dispatcherProvider.io) {
        db.checklistItemQueries.update(
            title = item.title,
            category = item.category,
            orderIndex = item.orderIndex,
            updatedAt = item.updatedAt,
            syncState = item.syncState.toDb(),
            id = item.id,
        )
    }

    override suspend fun setCompleted(id: String, isCompleted: Boolean, updatedAt: Long) =
        withContext(dispatcherProvider.io) {
            db.checklistItemQueries.setCompleted(isCompleted = isCompleted.toLong(), updatedAt = updatedAt, id = id)
        }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.checklistItemQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun ChecklistItemEntity.toDomain() = ChecklistItem(
        id = id,
        tripId = tripId,
        title = title,
        isCompleted = isCompleted.toBoolean(),
        category = category,
        orderIndex = orderIndex,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── Accommodation ────────────────────────────────────────────────────────────

interface AccommodationRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<Accommodation>
    suspend fun getByPlace(placeId: String): List<Accommodation>
    suspend fun getById(id: String): Accommodation?
    suspend fun insert(accommodation: Accommodation)
    suspend fun update(accommodation: Accommodation)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class AccommodationRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : AccommodationRepository {

    override suspend fun getByTrip(tripId: String): List<Accommodation> = withContext(dispatcherProvider.io) {
        db.accommodationQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByPlace(placeId: String): List<Accommodation> = withContext(dispatcherProvider.io) {
        db.accommodationQueries.selectByPlace(placeId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): Accommodation? = withContext(dispatcherProvider.io) {
        db.accommodationQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(accommodation: Accommodation) = withContext(dispatcherProvider.io) {
        db.accommodationQueries.insert(
            id = accommodation.id,
            tripId = accommodation.tripId,
            placeId = accommodation.placeId,
            name = accommodation.name,
            address = accommodation.address,
            checkInDate = accommodation.checkInDate.toString(),
            checkOutDate = accommodation.checkOutDate.toString(),
            confirmationCode = accommodation.confirmationCode,
            notes = accommodation.notes,
            updatedAt = accommodation.updatedAt,
            deleted = accommodation.deleted.toLong(),
            syncState = accommodation.syncState.toDb(),
            createdBy = accommodation.createdBy.toDb(),
        )
    }

    override suspend fun update(accommodation: Accommodation) = withContext(dispatcherProvider.io) {
        db.accommodationQueries.update(
            placeId = accommodation.placeId,
            name = accommodation.name,
            address = accommodation.address,
            checkInDate = accommodation.checkInDate.toString(),
            checkOutDate = accommodation.checkOutDate.toString(),
            confirmationCode = accommodation.confirmationCode,
            notes = accommodation.notes,
            updatedAt = accommodation.updatedAt,
            syncState = accommodation.syncState.toDb(),
            id = accommodation.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.accommodationQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun AccommodationEntity.toDomain() = Accommodation(
        id = id,
        tripId = tripId,
        placeId = placeId,
        name = name,
        address = address,
        checkInDate = LocalDate.parse(checkInDate),
        checkOutDate = LocalDate.parse(checkOutDate),
        confirmationCode = confirmationCode,
        notes = notes,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── DiaryEntry ───────────────────────────────────────────────────────────────

interface DiaryEntryRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<DiaryEntry>
    suspend fun getByDay(dayId: String): List<DiaryEntry>
    suspend fun getById(id: String): DiaryEntry?
    suspend fun insert(entry: DiaryEntry)
    suspend fun update(entry: DiaryEntry)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class DiaryEntryRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : DiaryEntryRepository {

    override suspend fun getByTrip(tripId: String): List<DiaryEntry> = withContext(dispatcherProvider.io) {
        db.diaryEntryQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getByDay(dayId: String): List<DiaryEntry> = withContext(dispatcherProvider.io) {
        db.diaryEntryQueries.selectByDay(dayId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): DiaryEntry? = withContext(dispatcherProvider.io) {
        db.diaryEntryQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(entry: DiaryEntry) = withContext(dispatcherProvider.io) {
        db.diaryEntryQueries.insert(
            id = entry.id,
            tripId = entry.tripId,
            dayId = entry.dayId,
            text = entry.text,
            photoLocalPaths = Json.encodeToString(entry.photoLocalPaths),
            date = entry.date.toString(),
            updatedAt = entry.updatedAt,
            deleted = entry.deleted.toLong(),
            syncState = entry.syncState.toDb(),
            createdBy = entry.createdBy.toDb(),
        )
    }

    override suspend fun update(entry: DiaryEntry) = withContext(dispatcherProvider.io) {
        db.diaryEntryQueries.update(
            text = entry.text,
            photoLocalPaths = Json.encodeToString(entry.photoLocalPaths),
            date = entry.date.toString(),
            updatedAt = entry.updatedAt,
            syncState = entry.syncState.toDb(),
            id = entry.id,
        )
    }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.diaryEntryQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun DiaryEntryEntity.toDomain() = DiaryEntry(
        id = id,
        tripId = tripId,
        dayId = dayId,
        text = text,
        photoLocalPaths = photoLocalPaths?.let { Json.decodeFromString(it) } ?: emptyList(),
        date = LocalDate.parse(date),
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── Reminder ─────────────────────────────────────────────────────────────────

interface ReminderRepository : NDRepository {
    suspend fun getByTrip(tripId: String): List<Reminder>
    suspend fun getPending(tripId: String): List<Reminder>
    suspend fun getById(id: String): Reminder?
    suspend fun insert(reminder: Reminder)
    suspend fun update(reminder: Reminder)
    suspend fun setTriggered(id: String, isTriggered: Boolean, updatedAt: Long)
    suspend fun softDelete(id: String, updatedAt: Long)
}

class ReminderRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : ReminderRepository {

    override suspend fun getByTrip(tripId: String): List<Reminder> = withContext(dispatcherProvider.io) {
        db.reminderQueries.selectByTrip(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getPending(tripId: String): List<Reminder> = withContext(dispatcherProvider.io) {
        db.reminderQueries.selectPending(tripId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getById(id: String): Reminder? = withContext(dispatcherProvider.io) {
        db.reminderQueries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(reminder: Reminder) = withContext(dispatcherProvider.io) {
        db.reminderQueries.insert(
            id = reminder.id,
            tripId = reminder.tripId,
            title = reminder.title,
            dateTime = reminder.dateTime.toString(),
            isTriggered = reminder.isTriggered.toLong(),
            flightId = reminder.flightId,
            trainTripId = reminder.trainTripId,
            activityId = reminder.activityId,
            updatedAt = reminder.updatedAt,
            deleted = reminder.deleted.toLong(),
            syncState = reminder.syncState.toDb(),
            createdBy = reminder.createdBy.toDb(),
        )
    }

    override suspend fun update(reminder: Reminder) = withContext(dispatcherProvider.io) {
        db.reminderQueries.update(
            title = reminder.title,
            dateTime = reminder.dateTime.toString(),
            updatedAt = reminder.updatedAt,
            syncState = reminder.syncState.toDb(),
            id = reminder.id,
        )
    }

    override suspend fun setTriggered(id: String, isTriggered: Boolean, updatedAt: Long) =
        withContext(dispatcherProvider.io) {
            db.reminderQueries.setTriggered(isTriggered = isTriggered.toLong(), updatedAt = updatedAt, id = id)
        }

    override suspend fun softDelete(id: String, updatedAt: Long) = withContext(dispatcherProvider.io) {
        db.reminderQueries.softDelete(updatedAt = updatedAt, id = id)
    }

    private fun ReminderEntity.toDomain() = Reminder(
        id = id,
        tripId = tripId,
        title = title,
        dateTime = LocalDateTime.parse(dateTime),
        isTriggered = isTriggered.toBoolean(),
        flightId = flightId,
        trainTripId = trainTripId,
        activityId = activityId,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

// ── Phrase ───────────────────────────────────────────────────────────────────

interface PhraseRepository : NDRepository {
    suspend fun getAll(): List<Phrase>
    suspend fun getByCategory(category: String): List<Phrase>
    suspend fun insert(phrase: Phrase)
}

class PhraseRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcherProvider: NDDispatcherProvider,
) : PhraseRepository {

    override suspend fun getAll(): List<Phrase> = withContext(dispatcherProvider.io) {
        db.phraseQueries.selectAll().executeAsList().map { it.toDomain() }
    }

    override suspend fun getByCategory(category: String): List<Phrase> = withContext(dispatcherProvider.io) {
        db.phraseQueries.selectByCategory(category).executeAsList().map { it.toDomain() }
    }

    override suspend fun insert(phrase: Phrase) = withContext(dispatcherProvider.io) {
        db.phraseQueries.insert(
            id = phrase.id,
            spanish = phrase.spanish,
            chinese = phrase.chinese,
            pinyin = phrase.pinyin,
            category = phrase.category,
            updatedAt = phrase.updatedAt,
            deleted = phrase.deleted.toLong(),
            syncState = phrase.syncState.toDb(),
            createdBy = phrase.createdBy.toDb(),
        )
    }

    private fun PhraseEntity.toDomain() = Phrase(
        id = id,
        spanish = spanish,
        chinese = chinese,
        pinyin = pinyin,
        category = category,
        updatedAt = updatedAt,
        deleted = deleted.toBoolean(),
        syncState = SyncState.fromDb(syncState),
        createdBy = Owner.fromDb(createdBy),
    )
}

private fun Boolean.toLong(): Long = if (this) 1L else 0L
private fun Long.toBoolean(): Boolean = this != 0L
