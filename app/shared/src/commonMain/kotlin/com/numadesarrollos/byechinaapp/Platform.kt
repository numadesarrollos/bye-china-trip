package com.numadesarrollos.byechinaapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform