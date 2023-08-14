package com.pkg.stockmarketapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CompanyDetailsDto(
    @SerializedName("Symbol")
    val symbol: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Address")
    val address: String,
    @SerializedName("Industry")
    val industry: String,
)