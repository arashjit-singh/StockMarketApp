package com.pkg.stockmarketapp.presentation.company_listings

import com.pkg.stockmarketapp.domain.modal.CompanyListing

data class CompanyListingState(
    val isLoading: Boolean = false,
    val companies: List<CompanyListing> = emptyList(),
    val message: String? = null,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
)
