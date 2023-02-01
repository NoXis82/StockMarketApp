package ru.umarsh.stockmarketapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.umarsh.stockmarketapp.domain.model.CompanyListing
import ru.umarsh.stockmarketapp.util.Resource

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}