package com.numadesarrollos.byechinaapp.auth

import com.numadesarrollos.base.domain.error.NDFailure

sealed class AuthFailure(message: String, cause: Throwable? = null) : NDFailure(message, cause) {
    class InvalidCredentials(cause: Throwable? = null) :
        AuthFailure("Email o contraseña incorrectos", cause)

    class UserNotFound(cause: Throwable? = null) :
        AuthFailure("Usuario no encontrado", cause)

    class NetworkError(cause: Throwable? = null) :
        AuthFailure("Sin conexión — comprueba la red", cause)

    class Unknown(message: String, cause: Throwable? = null) :
        AuthFailure(message, cause)
}
