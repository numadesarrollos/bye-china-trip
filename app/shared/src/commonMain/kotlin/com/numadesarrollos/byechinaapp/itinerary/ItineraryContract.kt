package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.presentation.mvi.NDUiEffect
import com.numadesarrollos.base.presentation.mvi.NDUiEvent
import com.numadesarrollos.base.presentation.mvi.NDUiState

data class ItineraryState(
    val isLoading: Boolean = true,
    val trip: Trip? = null,
    val places: List<PlaceWithDays> = emptyList(),
    val expandedPlaceIds: Set<String> = emptySet(),
) : NDUiState

sealed interface ItineraryEvent : NDUiEvent {
    data object CreateTripClicked : ItineraryEvent
    data class TogglePlace(val placeId: String) : ItineraryEvent
    data object AddPlaceClicked : ItineraryEvent
    data class AddDayClicked(val placeId: String) : ItineraryEvent
    data class DayClicked(val dayId: String) : ItineraryEvent
}

sealed interface ItineraryEffect : NDUiEffect {
    data object NavigateToCreateTrip : ItineraryEffect
    data class NavigateToAddPlace(val tripId: String) : ItineraryEffect
    data class NavigateToAddDay(val placeId: String) : ItineraryEffect
    data class NavigateToDay(val dayId: String) : ItineraryEffect
}
