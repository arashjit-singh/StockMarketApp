package com.pkg.stockmarketapp.data.mapper

import com.pkg.stockmarketapp.data.remote.dto.IntraDayInfoDto
import com.pkg.stockmarketapp.domain.modal.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntraDayInfoDto.toIntraDayInfo(): IntraDayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timeStamp, formatter)
    return IntraDayInfo(
        date = localDateTime,
        close = close
    )
}