package com.yoti.android.cryptocurrencychallenge.data

import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetsApiData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketsApiData
import retrofit2.http.GET

const val CAPCOIN_ENDPOINT_HOST = "https://api.coincap.io/"

interface CoincapService {

    @GET("/v2/assets")
    suspend fun getAssets(): AssetsApiData

    @GET("/v2/markets")
    suspend fun getMarkets(): MarketsApiData
}