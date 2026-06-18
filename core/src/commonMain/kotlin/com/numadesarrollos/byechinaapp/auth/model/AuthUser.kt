package com.numadesarrollos.byechinaapp.auth.model

data class AuthUser(
    val uid: String,
    val email: String,
    val displayName: String? = null,
)
