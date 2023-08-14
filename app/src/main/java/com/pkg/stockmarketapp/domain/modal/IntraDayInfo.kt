package com.pkg.stockmarketapp.domain.modal

import java.time.LocalDateTime

data class IntraDayInfo(
    val date: LocalDateTime,
    val close: Double,
)
