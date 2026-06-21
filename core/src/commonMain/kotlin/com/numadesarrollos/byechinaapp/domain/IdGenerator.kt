package com.numadesarrollos.byechinaapp.domain

import kotlin.random.Random

object IdGenerator {
    private const val HEX = "0123456789abcdef"

    fun newId(): String {
        val bytes = Random.nextBytes(16)
        val sb = StringBuilder(32)
        for (byte in bytes) {
            val i = byte.toInt() and 0xFF
            sb.append(HEX[i shr 4])
            sb.append(HEX[i and 0x0F])
        }
        return sb.toString()
    }
}
