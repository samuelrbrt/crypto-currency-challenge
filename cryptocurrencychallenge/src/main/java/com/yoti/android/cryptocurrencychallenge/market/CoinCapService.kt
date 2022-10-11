package com.yoti.android.cryptocurrencychallenge.market

import com.yoti.android.cryptocurrencychallenge.asset.AssetsApiData
import retrofit2.http.GET

interface CoinCapService {


    @GET("/v2/markets")
    suspend fun getMarkets(): MarketsApiData
}