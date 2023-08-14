package com.pkg.stockmarketapp.domain.repository

import com.pkg.stockmarketapp.domain.modal.CompanyDetails
import com.pkg.stockmarketapp.domain.modal.CompanyListing
import com.pkg.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun getCompanyListing(
        query: String,
        fetchFromRemote: Boolean,
    ): Flow<Resource<List<CompanyListing>>>

    fun getCompanyDetails(symbol: String): Flow<Resource<CompanyDetails>>
}