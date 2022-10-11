package com.yoti.android.cryptocurrencychallenge.asset


import com.google.gson.annotations.SerializedName
import com.yoti.android.cryptocurrencychallenge.asset.AssetData

data class AssetsApiData(
    @SerializedName("data")
        val assetData: List<AssetData>?,
    @SerializedName("timestamp")
        val timestamp: Long?
)