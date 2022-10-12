package com.yoti.android.cryptocurrencychallenge.market

import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {
    @GET("/v2/markets")
    suspend fun getMarkets(@Query("baseId") baseId: String): MarketsApiData
}