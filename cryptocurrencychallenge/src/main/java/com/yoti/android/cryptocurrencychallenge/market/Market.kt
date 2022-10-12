package com.yoti.android.cryptocurrencychallenge.market


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Market(
    @SerializedName("baseId")
    @PrimaryKey val baseId: String,
    @SerializedName("baseSymbol")
    val baseSymbol: String?,
    @SerializedName("exchangeId")
    val exchangeId: String?,
    @SerializedName("percentExchangeVolume")
    val percentExchangeVolume: Double?,
    @SerializedName("priceQuote")
    val priceQuote: String?,
    @SerializedName("priceUsd")
    val priceUsd: String?,
    @SerializedName("quoteId")
    val quoteId: String?,
    @SerializedName("quoteSymbol")
    val quoteSymbol: String?,
    @SerializedName("rank")
    val rank: String?,
    @SerializedName("tradesCount24Hr")
    val tradesCount24Hr: Long?,
    @SerializedName("updated")
    val updated: Long?,
    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: Double
)