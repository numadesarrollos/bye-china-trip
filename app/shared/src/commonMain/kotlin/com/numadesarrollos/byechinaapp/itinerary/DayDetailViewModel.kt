package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.presentation.mvi.NDViewModel

class DayDetailViewModel(
    private val dayId: String,
    private val getDayDetailUseCase: GetDayDetailUseCase,
    dispatcherProvider: NDDispatcherProvider,
) : NDViewModel<DayDetailState, DayDetailEvent, DayDetailEffect>(DayDetailState(), dispatcherProvider) {

    init {
        load()
    }

    override fun onEvent(event: DayDetailEvent) {
        when (event) {
            DayDetailEvent.Retry -> load()
        }
    }

    private fun load() {
        setState { copy(isLoading = true) }
        execute(
            onSuccess = { detail ->
                setState {
                    copy(
                        isLoading = false,
                        day = detail?.day,
                        activities = detail?.activities.orEmpty(),
                        locations = detail?.locations.orEmpty(),
                    )
                }
            },
            onError = { setState { copy(isLoading = false) } },
        ) {
            getDayDetailUseCase(GetDayDetailParams(dayId)).getOrThrow()
        }
    }
}
