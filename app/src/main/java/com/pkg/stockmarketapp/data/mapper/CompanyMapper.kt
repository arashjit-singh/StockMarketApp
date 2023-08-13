package com.pkg.stockmarketapp.data.mapper

import com.pkg.stockmarketapp.data.local.CompanyListingEntity
import com.pkg.stockmarketapp.domain.modal.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}