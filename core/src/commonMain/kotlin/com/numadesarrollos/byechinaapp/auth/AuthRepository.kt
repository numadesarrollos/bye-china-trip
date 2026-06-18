package com.numadesarrollos.byechinaapp.auth

import com.numadesarrollos.base.domain.repository.NDRepository
import com.numadesarrollos.byechinaapp.auth.model.AuthUser

interface AuthRepository : NDRepository {
    suspend fun signIn(email: String, password: String): AuthUser
    suspend fun signOut()
    fun isSignedIn(): Boolean
    fun currentUser(): AuthUser?
}
