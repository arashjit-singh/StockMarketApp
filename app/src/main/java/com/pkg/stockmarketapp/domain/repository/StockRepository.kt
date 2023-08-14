package com.pkg.stockmarketapp.domain.repository

import com.pkg.stockmarketapp.domain.modal.CompanyInfo
import com.pkg.stockmarketapp.domain.modal.CompanyListing
import com.pkg.stockmarketapp.domain.modal.IntraDayInfo
import com.pkg.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun getCompanyListing(
        query: String,
        fetchFromRemote: Boolean,
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo>
    suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>>
}