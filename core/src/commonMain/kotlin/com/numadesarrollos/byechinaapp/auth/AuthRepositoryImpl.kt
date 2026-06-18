package com.numadesarrollos.byechinaapp.auth

import com.numadesarrollos.byechinaapp.auth.model.AuthUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class AuthRepositoryImpl : AuthRepository {

    private val auth get() = Firebase.auth

    override suspend fun signIn(email: String, password: String): AuthUser {
        val result = auth.signInWithEmailAndPassword(email, password)
        val user = result.user
            ?: throw IllegalStateException("Login exitoso pero usuario es nulo")
        return AuthUser(
            uid = user.uid,
            email = user.email ?: email,
            displayName = user.displayName,
        )
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun isSignedIn(): Boolean = auth.currentUser != null

    override fun currentUser(): AuthUser? {
        val user = auth.currentUser ?: return null
        return AuthUser(
            uid = user.uid,
            email = user.email ?: "",
            displayName = user.displayName,
        )
    }
}
