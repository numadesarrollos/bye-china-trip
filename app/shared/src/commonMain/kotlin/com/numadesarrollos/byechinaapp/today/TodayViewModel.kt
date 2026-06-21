package com.numadesarrollos.byechinaapp.today

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.domain.usecase.invoke
import com.numadesarrollos.base.presentation.mvi.NDViewModel
import com.numadesarrollos.byechinaapp.domain.ChinaTime
import com.numadesarrollos.byechinaapp.itinerary.Activity
import com.numadesarrollos.byechinaapp.itinerary.Day
import com.numadesarrollos.byechinaapp.itinerary.GetDayDetailParams
import com.numadesarrollos.byechinaapp.itinerary.GetDayDetailUseCase
import com.numadesarrollos.byechinaapp.itinerary.GetTodayUseCase
import com.numadesarrollos.byechinaapp.itinerary.GetTripUseCase
import com.numadesarrollos.byechinaapp.itinerary.Trip
import kotlinx.datetime.daysUntil

private data class TodayLoadResult(
    val trip: Trip?,
    val today: Day?,
    val activities: List<Activity>,
    val tripProgress: TripProgress?,
)

class TodayViewModel(
    private val getTripUseCase: GetTripUseCase,
    private val getTodayUseCase: GetTodayUseCase,
    private val getDayDetailUseCase: GetDayDetailUseCase,
    dispatcherProvider: NDDispatcherProvider,
) : NDViewModel<TodayState, TodayEvent, TodayEffect>(TodayState(), dispatcherProvider) {

    init {
        reload()
    }

    override fun onEvent(event: TodayEvent) {
        when (event) {
            TodayEvent.CreateTripClicked -> setEffect { TodayEffect.NavigateToCreateTrip }
            TodayEvent.TodayCardClicked -> {
                currentState.today?.let { day ->
                    setEffect { TodayEffect.NavigateToDay(day.id) }
                }
            }
        }
    }

    fun reload() {
        setState { copy(isLoading = true) }
        execute(
            onSuccess = { result ->
                setState {
                    copy(
                        isLoading = false,
                        trip = result.trip,
                        today = result.today,
                        activities = result.activities,
                        tripProgress = result.tripProgress,
                    )
                }
            },
            onError = { setState { copy(isLoading = false) } },
        ) {
            val trip = getTripUseCase().getOrThrow()
            if (trip == null) {
                TodayLoadResult(null, null, emptyList(), null)
            } else {
                val today = getTodayUseCase().getOrThrow()
                val activities = today
                    ?.let { getDayDetailUseCase(GetDayDetailParams(it.id)).getOrThrow()?.activities }
                    .orEmpty()
                val tripProgress = tripProgressOf(trip)
                TodayLoadResult(trip, today, activities, tripProgress)
            }
        }
    }

    private fun tripProgressOf(trip: Trip): TripProgress {
        val now = ChinaTime.today()
        return when {
            now < trip.startDate -> TripProgress.BeforeTrip(daysUntilStart = now.daysUntil(trip.startDate))
            now > trip.endDate -> TripProgress.AfterTrip
            else -> TripProgress.DuringTrip(
                dayNumber = trip.startDate.daysUntil(now) + 1,
                totalDays = trip.startDate.daysUntil(trip.endDate) + 1,
            )
        }
    }
}
