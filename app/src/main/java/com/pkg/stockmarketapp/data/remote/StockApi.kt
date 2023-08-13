package com.pkg.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apikey: String,
    ): ResponseBody

    companion object {
        const val API_KEY = "BM3HGN327OZRFOM1"
    }
}