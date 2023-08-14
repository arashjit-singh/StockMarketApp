package com.pkg.stockmarketapp.presentation.company_info

import com.pkg.stockmarketapp.domain.modal.CompanyInfo
import com.pkg.stockmarketapp.domain.modal.IntraDayInfo

data class CompanyInfoState(
    val stockInfo: List<IntraDayInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
