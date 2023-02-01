package ru.umarsh.stockmarketapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.umarsh.stockmarketapp.data.csv.CSVParser
import ru.umarsh.stockmarketapp.data.csv.CompanyListingsParser
import ru.umarsh.stockmarketapp.data.csv.IntraDayInfoParser
import ru.umarsh.stockmarketapp.data.repository.StockRepositoryImpl
import ru.umarsh.stockmarketapp.domain.model.CompanyListing
import ru.umarsh.stockmarketapp.domain.model.IntraDayInfo
import ru.umarsh.stockmarketapp.domain.repository.StockRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(companyListingsParser: CompanyListingsParser): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

    @Binds
    @Singleton
    abstract fun bindIntraDayInfoParser(intraDayInfoParser: IntraDayInfoParser): CSVParser<IntraDayInfo>
}