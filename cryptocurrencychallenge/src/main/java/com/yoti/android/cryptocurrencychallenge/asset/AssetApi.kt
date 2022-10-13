package com.yoti.android.cryptocurrencychallenge.asset

import retrofit2.http.GET
import retrofit2.http.Query

interface AssetApi {
    @GET("/v2/assets")
    suspend fun getAssets(@Query("offset") offset: Int, @Query("limit") limit: Int): AssetsApiData
}