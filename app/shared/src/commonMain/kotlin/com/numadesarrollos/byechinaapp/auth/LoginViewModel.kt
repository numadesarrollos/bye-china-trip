package com.numadesarrollos.byechinaapp.auth

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.presentation.mvi.NDViewModel

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    dispatcherProvider: NDDispatcherProvider,
) : NDViewModel<LoginState, LoginEvent, LoginEffect>(LoginState(), dispatcherProvider) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged    -> setState { copy(email = event.email) }
            is LoginEvent.PasswordChanged -> setState { copy(password = event.password) }
            is LoginEvent.LoginClicked    -> login()
            is LoginEvent.ErrorDismissed  -> setState { copy(error = null) }
        }
    }

    private fun login() {
        setState { copy(isLoading = true, error = null) }
        execute(
            onSuccess = {
                setState { copy(isLoading = false) }
                setEffect { LoginEffect.NavigateToHome }
            },
            onError = { throwable ->
                setState { copy(isLoading = false, error = throwable.message) }
            },
        ) {
            loginUseCase(LoginParams(currentState.email, currentState.password)).getOrThrow()
        }
    }
}
