package com.pkg.stockmarketapp.data.remote

import com.pkg.stockmarketapp.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apikey: String = API_KEY,
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("apikey") apikey: String = API_KEY,
        @Query("symbol") symbol: String,
    ): CompanyInfoDto

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntraDayInfo(
        @Query("apikey") apikey: String = API_KEY,
        @Query("symbol") symbol: String,
    ): ResponseBody

    companion object {
        const val API_KEY = "BM3HGN327OZRFOM1"
    }
}