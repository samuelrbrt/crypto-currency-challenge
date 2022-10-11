package com.yoti.android.cryptocurrencychallenge.config.coroutine

import kotlinx.coroutines.CoroutineDispatcher

open class CoroutineDispatchers : Dispatchers {
    override val Main: CoroutineDispatcher by lazy { kotlinx.coroutines.Dispatchers.Main }
    override val IO: CoroutineDispatcher by lazy { kotlinx.coroutines.Dispatchers.IO }
    override val Default: CoroutineDispatcher by lazy { kotlinx.coroutines.Dispatchers.Default }
    override val Unconfined: CoroutineDispatcher by lazy { kotlinx.coroutines.Dispatchers.Unconfined }
}
