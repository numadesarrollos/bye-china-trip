package com.numadesarrollos.byechinaapp.auth

import com.numadesarrollos.base.presentation.mvi.NDUiEffect
import com.numadesarrollos.base.presentation.mvi.NDUiEvent
import com.numadesarrollos.base.presentation.mvi.NDUiState

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
) : NDUiState

sealed interface LoginEvent : NDUiEvent {
    data class EmailChanged(val email: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data object LoginClicked : LoginEvent
    data object ErrorDismissed : LoginEvent
}

sealed interface LoginEffect : NDUiEffect {
    data object NavigateToHome : LoginEffect
}
