package com.yoti.android.cryptocurrencychallenge.market

import com.yoti.android.cryptocurrencychallenge.config.coroutine.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.io.IOException
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

    private suspend fun shouldRetryWithIoException(cause: Throwable, attempt: Long): Boolean {
        return if (cause is IOException && attempt < 3) {
            delay(3000)
            true
        } else false
    }
}
