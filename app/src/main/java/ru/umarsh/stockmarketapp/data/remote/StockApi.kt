package ru.umarsh.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import ru.umarsh.stockmarketapp.data.remote.dto.CompanyInfoDto

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListingsStatus(
        @Query("apikey") apiKey: String
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): CompanyInfoDto
}