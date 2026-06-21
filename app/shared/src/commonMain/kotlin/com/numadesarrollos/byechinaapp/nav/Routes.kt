package com.numadesarrollos.byechinaapp.nav

object Routes {
    const val LOGIN = "login"
    const val TODAY = "today"
    const val ITINERARY = "itinerary"
    const val FLIGHTS = "flights"
    const val TRAINS = "trains"
    const val MORE = "more"

    const val TRIP_FORM = "trip/form"

    const val PLACE_FORM_PATTERN = "place/form/{tripId}"
    fun placeForm(tripId: String) = "place/form/$tripId"

    const val DAY_FORM_PATTERN = "day/form/{placeId}"
    fun dayForm(placeId: String) = "day/form/$placeId"

    const val DAY_DETAIL_PATTERN = "day/{dayId}"
    fun dayDetail(dayId: String) = "day/$dayId"

    val bottomNavRoutes = setOf(TODAY, ITINERARY, FLIGHTS, TRAINS, MORE)
}
