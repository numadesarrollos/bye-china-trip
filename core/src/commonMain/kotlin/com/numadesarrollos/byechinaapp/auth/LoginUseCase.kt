package com.numadesarrollos.byechinaapp.auth

import com.numadesarrollos.base.domain.result.NDResult
import com.numadesarrollos.base.domain.usecase.NDParams
import com.numadesarrollos.base.domain.usecase.NDUseCase
import com.numadesarrollos.byechinaapp.auth.model.AuthUser

data class LoginParams(
    val email: String,
    val password: String,
) : NDParams

class LoginUseCase(
    private val authRepository: AuthRepository,
) : NDUseCase<LoginParams, AuthUser>() {

    override suspend fun doWork(params: LoginParams): NDResult<AuthUser> =
        NDResult.suspendRunCatching {
            authRepository.signIn(params.email, params.password)
        }
}
