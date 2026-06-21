package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.domain.usecase.invoke
import com.numadesarrollos.base.presentation.mvi.NDViewModel

private data class ItineraryLoadResult(
    val trip: Trip?,
    val places: List<PlaceWithDays>,
    val expandedPlaceIds: Set<String>,
)

class ItineraryViewModel(
    private val getTripUseCase: GetTripUseCase,
    private val getItineraryUseCase: GetItineraryUseCase,
    private val getTodayUseCase: GetTodayUseCase,
    dispatcherProvider: NDDispatcherProvider,
) : NDViewModel<ItineraryState, ItineraryEvent, ItineraryEffect>(ItineraryState(), dispatcherProvider) {

    init {
        reload()
    }

    override fun onEvent(event: ItineraryEvent) {
        when (event) {
            ItineraryEvent.CreateTripClicked -> setEffect { ItineraryEffect.NavigateToCreateTrip }
            is ItineraryEvent.TogglePlace -> setState {
                val expanded = if (event.placeId in expandedPlaceIds) {
                    expandedPlaceIds - event.placeId
                } else {
                    expandedPlaceIds + event.placeId
                }
                copy(expandedPlaceIds = expanded)
            }
            ItineraryEvent.AddPlaceClicked -> currentState.trip?.let { trip ->
                setEffect { ItineraryEffect.NavigateToAddPlace(trip.id) }
            }
            is ItineraryEvent.AddDayClicked -> setEffect { ItineraryEffect.NavigateToAddDay(event.placeId) }
            is ItineraryEvent.DayClicked -> setEffect { ItineraryEffect.NavigateToDay(event.dayId) }
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
                        places = result.places,
                        expandedPlaceIds = result.expandedPlaceIds,
                    )
                }
            },
            onError = { setState { copy(isLoading = false) } },
        ) {
            val trip = getTripUseCase().getOrThrow()
            if (trip == null) {
                ItineraryLoadResult(null, emptyList(), emptySet())
            } else {
                val places = getItineraryUseCase(GetItineraryParams(trip.id)).getOrThrow()
                val today = getTodayUseCase().getOrThrow()
                val todayPlaceId = today?.let { day ->
                    places.firstOrNull { it.days.any { d -> d.id == day.id } }?.place?.id
                }
                ItineraryLoadResult(trip, places, todayPlaceId?.let { setOf(it) } ?: emptySet())
            }
        }
    }
}
