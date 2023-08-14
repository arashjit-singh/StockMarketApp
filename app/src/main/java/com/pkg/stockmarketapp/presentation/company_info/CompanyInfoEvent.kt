package com.pkg.stockmarketapp.presentation.company_info

sealed class CompanyInfoEvent {
    data class UpdateSymbol(val symbol: String) : CompanyInfoEvent()
}