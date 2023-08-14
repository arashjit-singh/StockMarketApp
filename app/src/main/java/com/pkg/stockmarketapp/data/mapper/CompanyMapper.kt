package com.pkg.stockmarketapp.data.mapper

import com.pkg.stockmarketapp.data.local.CompanyListingEntity
import com.pkg.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.pkg.stockmarketapp.domain.modal.CompanyInfo
import com.pkg.stockmarketapp.domain.modal.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name, symbol = symbol, exchange = exchange
    )
}

fun CompanyListing.toCompanyEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name, symbol = symbol, exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        name = name ?: "",
        description = description ?: "",
        country = country ?: "",
        address = address ?: "",
        industry = industry ?: "",
    )
}
