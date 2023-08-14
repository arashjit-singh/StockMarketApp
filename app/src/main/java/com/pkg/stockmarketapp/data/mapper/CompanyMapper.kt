package com.pkg.stockmarketapp.data.mapper

import com.pkg.stockmarketapp.data.local.CompanyListingEntity
import com.pkg.stockmarketapp.data.remote.dto.CompanyDetailsDto
import com.pkg.stockmarketapp.domain.modal.CompanyDetails
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

fun CompanyDetailsDto.toCompanyDetails(): CompanyDetails {
    return CompanyDetails(
        symbol = symbol,
        name = name,
        description = description,
        country = country,
        address = address,
        industry = industry
    )
}