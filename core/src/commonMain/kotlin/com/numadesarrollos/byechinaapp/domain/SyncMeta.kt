package com.numadesarrollos.byechinaapp.domain

enum class SyncState {
    SYNCED,
    PENDING_UPLOAD;

    fun toDb(): String = when (this) {
        SYNCED -> "synced"
        PENDING_UPLOAD -> "pendingUpload"
    }

    companion object {
        fun fromDb(value: String): SyncState = when (value) {
            "synced" -> SYNCED
            else -> PENDING_UPLOAD
        }
    }
}

enum class Owner {
    BEAR, // 🐻 Borja
    BUN;  // 🐰 Esther

    fun toDb(): String = when (this) {
        BEAR -> "bear"
        BUN -> "bun"
    }

    companion object {
        fun fromDb(value: String): Owner = when (value) {
            "bun" -> BUN
            else -> BEAR
        }
    }
}
