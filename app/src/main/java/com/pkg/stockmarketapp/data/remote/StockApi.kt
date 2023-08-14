package com.pkg.stockmarketapp.data.remote

import com.pkg.stockmarketapp.data.remote.dto.CompanyDetailsDto
import com.pkg.stockmarketapp.util.Resource
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apikey: String = API_KEY,
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyDetails(
        @Query("apikey") apikey: String = API_KEY,
        @Query("symbol") symbol: String,
    ): CompanyDetailsDto

    companion object {
        const val API_KEY = "BM3HGN327OZRFOM1"
    }
}