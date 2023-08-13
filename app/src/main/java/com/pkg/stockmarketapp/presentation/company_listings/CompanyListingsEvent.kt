package com.pkg.stockmarketapp.presentation.company_listings

sealed class CompanyListingsEvent {
    object Refresh : CompanyListingsEvent()
    data class OnQueryChange(val query: String) : CompanyListingsEvent()
}
