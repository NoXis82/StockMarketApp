package ru.umarsh.stockmarketapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.umarsh.stockmarketapp.BuildConfig
import ru.umarsh.stockmarketapp.data.local.StockDataBase
import ru.umarsh.stockmarketapp.data.mapper.toCompanyListing
import ru.umarsh.stockmarketapp.data.remote.StockApi
import ru.umarsh.stockmarketapp.domain.model.CompanyListing
import ru.umarsh.stockmarketapp.domain.repository.StockRepository
import ru.umarsh.stockmarketapp.util.Resource
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val stockApi: StockApi,
    val stockDataBase: StockDataBase
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
                response.byteStream()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(">>> Error: ${e.message}"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(">>> Error: ${e.message}"))
            }
        }

    }
}