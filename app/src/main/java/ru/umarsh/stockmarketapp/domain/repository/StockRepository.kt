package ru.umarsh.stockmarketapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.umarsh.stockmarketapp.domain.model.CompanyInfo
import ru.umarsh.stockmarketapp.domain.model.CompanyListing
import ru.umarsh.stockmarketapp.domain.model.IntraDayInfo
import ru.umarsh.stockmarketapp.util.Resource

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}