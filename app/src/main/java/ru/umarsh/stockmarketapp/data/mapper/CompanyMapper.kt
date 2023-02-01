package ru.umarsh.stockmarketapp.data.mapper

import ru.umarsh.stockmarketapp.data.local.CompanyListingEntity
import ru.umarsh.stockmarketapp.data.remote.dto.CompanyInfoDto
import ru.umarsh.stockmarketapp.domain.model.CompanyInfo
import ru.umarsh.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing =
    CompanyListing(name = name, symbol = symbol, exchange = exchange)

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity =
    CompanyListingEntity(name = name, symbol = symbol, exchange = exchange)

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}