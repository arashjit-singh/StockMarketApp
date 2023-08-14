package com.pkg.stockmarketapp.domain.modal

data class CompanyDetails(
    val symbol: String,
    val name: String,
    val description: String,
    val exchange: String,
    val currency: String,
    val country: String,
    val address: String,
)
