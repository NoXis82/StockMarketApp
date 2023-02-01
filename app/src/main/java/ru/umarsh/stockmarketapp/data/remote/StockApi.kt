package ru.umarsh.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListingsStatus(
        @Query("apikey") apiKey: String
    ): ResponseBody

}