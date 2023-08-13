package com.pkg.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.pkg.stockmarketapp.di.IoDispatcher
import com.pkg.stockmarketapp.domain.modal.CompanyListing
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader

class CompanyListingParser(@IoDispatcher private val dispatcher: CoroutineDispatcher) :
    CsvParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(dispatcher) {
            csvReader.readAll().drop(1).mapNotNull { line ->
                val symbol = line.getOrNull(0)
                val name = line.getOrNull(1)
                val exchange = line.getOrNull(2)

                CompanyListing(
                    name = name ?: return@mapNotNull null,
                    symbol = symbol ?: return@mapNotNull null,
                    exchange = exchange ?: return@mapNotNull null,
                )
            }
        }.also {
            csvReader.close()
        }
    }

}