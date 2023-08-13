package com.pkg.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface StockDao {
    @Query("SELECT * from CompanyTable")
    suspend fun getAllListings(): List<CompanyListingEntity>

    @Upsert
    suspend fun insertCompany(list: List<CompanyListingEntity>)

    @Query("DELETE from CompanyTable")
    suspend fun clearTable()

    @Query(
        """ 
        SELECT * from CompanyTable
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
        UPPER(:query) == symbol
    """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>
}
