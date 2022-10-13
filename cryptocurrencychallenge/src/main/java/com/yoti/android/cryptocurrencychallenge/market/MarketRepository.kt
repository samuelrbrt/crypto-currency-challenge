package com.yoti.android.cryptocurrencychallenge.market

import com.yoti.android.cryptocurrencychallenge.config.coroutine.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MarketRepository @Inject constructor(
    private val db: MarketDao,
    private val api: MarketApi,
    private val dispatchers: Dispatchers
) {
    fun getMarket(baseId: String) = db.getMarket(baseId)

    fun refreshMarket(baseId: String) =
        flow { emit(api.getMarkets(baseId)) }
            .map { it.marketData.maxBy { market -> market.volumeUsd24Hr } }
            .onEach { db.upsert(it) }
            .flowOn(dispatchers.IO)
}
