package com.yoti.android.cryptocurrencychallenge.asset

import retrofit2.http.GET

interface AssetApi {
    @GET("/v2/assets")
    suspend fun getAssets(): AssetsApiData
}