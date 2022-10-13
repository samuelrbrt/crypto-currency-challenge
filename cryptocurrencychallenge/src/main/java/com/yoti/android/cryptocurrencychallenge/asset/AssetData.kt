package com.yoti.android.cryptocurrencychallenge.asset


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class AssetData(
    @SerializedName("id")
    @PrimaryKey val id: String,

    @SerializedName("changePercent24Hr")
    val changePercent24Hr: String? = null,
    @SerializedName("explorer")
    val explorer: String? = null,
    @SerializedName("marketCapUsd")
    val marketCapUsd: String? = null,
    @SerializedName("maxSupply")
    val maxSupply: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("priceUsd")
    val priceUsd: String? = null,
    @SerializedName("rank")
    val rank: Long = 0,
    @SerializedName("supply")
    val supply: String? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: String? = null,
    @SerializedName("vwap24Hr")
    val vwap24Hr: String? = null
)