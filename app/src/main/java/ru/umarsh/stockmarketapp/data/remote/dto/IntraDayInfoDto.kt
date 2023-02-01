package ru.umarsh.stockmarketapp.data.remote.dto

import com.squareup.moshi.Json
import java.time.LocalDateTime

data class IntraDayInfoDto(
    val timestamp: String,
    val close: Double
)
