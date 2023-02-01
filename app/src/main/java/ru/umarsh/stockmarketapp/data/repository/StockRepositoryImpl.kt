package ru.umarsh.stockmarketapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.umarsh.stockmarketapp.BuildConfig
import ru.umarsh.stockmarketapp.data.csv.CSVParser
import ru.umarsh.stockmarketapp.data.local.StockDataBase
import ru.umarsh.stockmarketapp.data.mapper.toCompanyInfo
import ru.umarsh.stockmarketapp.data.mapper.toCompanyListing
import ru.umarsh.stockmarketapp.data.mapper.toCompanyListingEntity
import ru.umarsh.stockmarketapp.data.remote.StockApi
import ru.umarsh.stockmarketapp.domain.model.CompanyInfo
import ru.umarsh.stockmarketapp.domain.model.CompanyListing
import ru.umarsh.stockmarketapp.domain.model.IntraDayInfo
import ru.umarsh.stockmarketapp.domain.repository.StockRepository
import ru.umarsh.stockmarketapp.util.Resource
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi,
    private val stockDataBase: StockDataBase,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intraDayInfoParser: CSVParser<IntraDayInfo>
) : StockRepository {

    private val stockDao = stockDataBase.stockDao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = stockDao.searchCompanyListing(query)
            emit(Resource.Success(data = localListings.map { it.toCompanyListing() }))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = stockApi.getListingsStatus(BuildConfig.API_KEY)
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(">>> Error: ${e.message}"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(">>> Error: ${e.message}"))
                null
            }
            remoteListings?.let { listings ->
                stockDao.clear()
                stockDao.insertCompanyListings(listings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(stockDao.searchCompanyListing("").map {
                    it.toCompanyListing()
                }))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = stockApi.getCompanyInfo(symbol, BuildConfig.API_KEY)
            Resource.Success(data = response.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(">>> Error: ${e.message}")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(">>> Error: ${e.message}")
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val response = stockApi.getIntradayInfo(symbol, BuildConfig.API_KEY)
            val result = intraDayInfoParser.parse(response.byteStream())
            Resource.Success(data = result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(">>> Error: ${e.message}")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(">>> Error: ${e.message}")
        }
    }
}