package com.pkg.stockmarketapp.domain.modal

data class CompanyInfo(
    val symbol: String,
    val name: String,
    val description: String,
    val country: String,
    val address: String,
    val industry: String,
)
