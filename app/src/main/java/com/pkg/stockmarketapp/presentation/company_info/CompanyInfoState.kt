package com.pkg.stockmarketapp.presentation.company_info

import com.pkg.stockmarketapp.domain.modal.CompanyDetails

data class CompanyInfoState(
    val isLoading: Boolean = false,
    val companyInfo: CompanyDetails? = null,
    val error: String? = null,
    val symbol: String = "",
)
