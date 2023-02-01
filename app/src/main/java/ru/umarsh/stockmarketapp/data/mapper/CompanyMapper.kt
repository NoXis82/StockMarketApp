package ru.umarsh.stockmarketapp.data.mapper

import ru.umarsh.stockmarketapp.data.local.CompanyListingEntity
import ru.umarsh.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing =
    CompanyListing(name = name, symbol = symbol, exchange = exchange)

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity =
    CompanyListingEntity(name = name, symbol = symbol, exchange = exchange)
