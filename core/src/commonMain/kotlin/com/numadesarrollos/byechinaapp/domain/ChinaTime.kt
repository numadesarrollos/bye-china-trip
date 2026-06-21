package com.numadesarrollos.byechinaapp.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

// China (mainland) uses a single timezone year-round, no DST
object ChinaTime {
    val zone: TimeZone = TimeZone.of("Asia/Shanghai")

    fun today(): LocalDate = Clock.System.now().toLocalDateTime(zone).date

    fun nowEpochMillis(): Long = Clock.System.now().toEpochMilliseconds()
}
