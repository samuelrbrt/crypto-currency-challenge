package com.yoti.android.cryptocurrencychallenge.market


import com.google.gson.annotations.SerializedName

data class MarketsApiData(
    @SerializedName("data")
    val marketData: List<Market>,
    @SerializedName("timestamp")
    val timestamp: Long?
)