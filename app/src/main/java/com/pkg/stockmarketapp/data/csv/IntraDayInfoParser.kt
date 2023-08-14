package com.pkg.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.pkg.stockmarketapp.data.mapper.toIntraDayInfo
import com.pkg.stockmarketapp.data.remote.dto.IntraDayInfoDto
import com.pkg.stockmarketapp.di.IoDispatcher
import com.pkg.stockmarketapp.domain.modal.CompanyListing
import com.pkg.stockmarketapp.domain.modal.IntraDayInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime

class IntraDayInfoParser(@IoDispatcher private val dispatcher: CoroutineDispatcher) :
    CsvParser<IntraDayInfo> {
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(dispatcher) {
            csvReader.readAll().drop(1).mapNotNull { line ->
                val timeStamp = line.getOrNull(0) ?: return@mapNotNull null
                val close = line.getOrNull(4) ?: return@mapNotNull null

                val dto = IntraDayInfoDto(timeStamp, close.toDouble())
                dto.toIntraDayInfo()
            }.filter {
                it.date.month == LocalDateTime.now().minusDays(1).month
            }
                .sortedBy {
                    it.date.hour
                }
        }.also {
            csvReader.close()
        }
    }

}