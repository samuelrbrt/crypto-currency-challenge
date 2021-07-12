package com.yoti.android.cryptocurrencychallenge.data.model.markets


import com.google.gson.annotations.SerializedName

data class MarketsApiData(
        @SerializedName("data")
        val marketData: List<MarketData>?,
        @SerializedName("timestamp")
        val timestamp: Long?
)