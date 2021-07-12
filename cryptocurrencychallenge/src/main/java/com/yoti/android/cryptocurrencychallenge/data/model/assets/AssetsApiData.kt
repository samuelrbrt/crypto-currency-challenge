package com.yoti.android.cryptocurrencychallenge.data.model.assets


import com.google.gson.annotations.SerializedName

data class AssetsApiData(
        @SerializedName("data")
        val assetData: List<AssetData>?,
        @SerializedName("timestamp")
        val timestamp: Long?
)